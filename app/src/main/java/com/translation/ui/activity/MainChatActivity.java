package com.translation.ui.activity;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.translation.R;
import com.translation.androidlib.manager.TapeRecordManager;
import com.translation.androidlib.observer.EventMsg;
import com.translation.androidlib.utils.AmrFileDecoder;
import com.translation.androidlib.utils.FileUtil;
import com.translation.androidlib.utils.IMUtil;
import com.translation.androidlib.utils.LogUtil;
import com.translation.androidlib.utils.TimeUtil;
import com.translation.androidlib.utils.ToastUtil;
import com.translation.androidlib.utils.TransformUtil;
import com.translation.androidlib.widget.CustomCountDownTimer;
import com.translation.androidlib.widget.CustomTextWatcher;
import com.translation.androidlib.widget.KeyboardRelativeLayout;
import com.translation.androidlib.widget.MicImageView;
import com.translation.component.base.BaseActivity;
import com.translation.component.constant.ContactCons;
import com.translation.component.permission.PermissionCallback;
import com.translation.component.permission.Permissions;
import com.translation.model.db.dao.MessageDao;
import com.translation.model.entity.ChatMsg;
import com.translation.model.entity.FileInfo;
import com.translation.model.entity.FriendInfo;
import com.translation.ui.MainActivity;
import com.translation.ui.adapter.MessageChatRcvAdapter;
import com.yanzhenjie.permission.Permission;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainChatActivity extends BaseActivity {

    private RecyclerView containerRcv;
    private EditText inputEt;
    private MicImageView tapeRecordIv;
    private ImageView voiceCallIv, moreIv, emoticonIv, sendMsgIv, recordPressIv, recordDeleteIv,
            leftSlideIv;
    private TextView voiceTimingTv, leftSlidePromptTv;
    private FrameLayout inputContainerFl;
    private LinearLayoutManager layoutManager;

    private MessageChatRcvAdapter rcvAdapter;
    private List<ChatMsg> msgList = new ArrayList<>();
    private FriendInfo chatInfo;

    private long startRecordTime;//录音起始时间戳
    private int tapeRecordSecond = 0;//录音秒数
    private final int LEFT_SLIDE_AVAILABLE_X = -30;//左滑取消录音的起始X坐标
    private final int MAX_VOICE_RECORD_SECOND = 60 * 1000;//最大录音时长
    private final int PRESS_AVAILABLE_SECOND = 150;//按住150ms后才开始录音
    private boolean isTapeRecordAvailable = true;//录音键/发送键切换
    private boolean isTapeRecordPermit = false;//录音权限是否开启
    private boolean isTapeRecordStop = true;//录音停止/取消


    private CustomCountDownTimer recordCountDown = new CustomCountDownTimer(MAX_VOICE_RECORD_SECOND + 2000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            voiceTimingTv.setText(TimeUtil.formatTimeStr(tapeRecordSecond));
            tapeRecordSecond++;
        }

        @Override
        protected void onFinish() {
            tapeRecordIv.setEnabled(false);
            resetTapeRecordView();
            RecordManager.getInstance().stop();
        }
    };

    private CustomCountDownTimer pressCountDown = new CustomCountDownTimer(PRESS_AVAILABLE_SECOND, 150) {
        @Override
        public void onFinish() {
            moreIv.setVisibility(View.GONE);
            voiceCallIv.setVisibility(View.GONE);
            inputContainerFl.setVisibility(View.GONE);

            recordPressIv.setVisibility(View.VISIBLE);
            recordDeleteIv.setVisibility(View.VISIBLE);
            voiceTimingTv.setVisibility(View.VISIBLE);
            leftSlideIv.setVisibility(View.VISIBLE);
            leftSlidePromptTv.setVisibility(View.VISIBLE);

            recordCountDown.start();

            // TODO: 2019/3/31 开始录音
            RecordManager.getInstance().start();

            startRecordTime = System.currentTimeMillis();

            AnimationDrawable animDrawable = (AnimationDrawable) leftSlideIv.getDrawable();
            if (animDrawable != null) {
                animDrawable.start();
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_conversation_chat;
    }

    @Override
    protected void getIntentData() {
        chatInfo = (FriendInfo) getIntent().getSerializableExtra(ContactCons.CONTACT_FRIEND);
    }

    @Override
    protected void initViews() {
        containerRcv = findViewById(R.id.rcv_conversation_chat_container);
        inputEt = findViewById(R.id.et_conversation_chat_input);
        emoticonIv = findViewById(R.id.iv_conversation_chat_emoticon);
        tapeRecordIv = findViewById(R.id.iv_conversation_chat_tape_record);
        voiceCallIv = findViewById(R.id.iv_conversation_chat_voice_call);
        moreIv = findViewById(R.id.iv_conversation_chat_more);
        sendMsgIv = findViewById(R.id.iv_conversation_chat_send);
        recordPressIv = findViewById(R.id.iv_conversation_chat_tape_record_press);
        recordDeleteIv = findViewById(R.id.iv_conversation_chat_tape_record_delete);
        voiceTimingTv = findViewById(R.id.tv_conversation_chat_tape_record_timing);
        leftSlideIv = findViewById(R.id.iv_conversation_chat_tape_record_anim);
        leftSlidePromptTv = findViewById(R.id.tv_conversation_chat_tape_record_left_slide_prompt);
        inputContainerFl = findViewById(R.id.fl_conversation_chat_input_container);
    }

    @Override
    protected void initViewsData() {
        setTitle(true, chatInfo.getNickname());
        initRecyclerView();

    }

    @Override
    protected void setListener() {
        emoticonIv.setOnClickListener(this);
        voiceCallIv.setOnClickListener(this);
        inputEt.setOnClickListener(this);
        moreIv.setOnClickListener(this);
        sendMsgIv.setOnClickListener(this);

        inputEt.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                isTapeRecordAvailable = !(inputEt.getText().toString().trim().length() > 0);
                if (isTapeRecordAvailable) {
                    tapeRecordIv.setVisibility(View.VISIBLE);
                    sendMsgIv.setVisibility(View.INVISIBLE);
                } else {
                    tapeRecordIv.setVisibility(View.INVISIBLE);
                    sendMsgIv.setVisibility(View.VISIBLE);
                }
            }
        });

        RecordManager.getInstance().changeRecordDir(FileUtil.getSDVoiceRecordPath(getAppContext()));
        RecordManager.getInstance().setRecordResultListener(new RecordResultListener() {
            @Override
            public void onResult(File result) {
                LogUtil.i("录音文件", result.getAbsolutePath());

                String filePath = result.getAbsolutePath();

                final TransformUtil transformUtil = new TransformUtil(getAppContext());
                transformUtil.voiceToText(new File(filePath), TransformUtil.ZH_CN, TransformUtil.EN);
                transformUtil.setOnTransformListener(new TransformUtil.OnTransformListener() {
                    @Override
                    public void onTransform(String results, String fileString, int type) {
                        LogUtil.i("transform results", results);
                        LogUtil.i("transform fileString", fileString);
                        if (isTapeRecordStop && type == TransformUtil.VOICE_TO_TEXT) {
                            IMUtil.getInstance().sendText(getAppContext(), results, tapeRecordSecond,
                                    fileString, chatInfo, TransformUtil.ZH_CN);
                        }
                        ToastUtil.showShort(getAppContext(), results);
                    }
                });
            }
        });

        //长按录音
        tapeRecordIv.setOnTouchListener(new MicImageView.OnTouchListener() {
            @Override
            public void actionDown() {
                Permissions.from(MainChatActivity.this, Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE, Permission.RECORD_AUDIO)
                        .start(new PermissionCallback() {
                            @Override
                            public void yes(List<String> data) {
                                isTapeRecordPermit = true;
                            }

                            @Override
                            public void no(List<String> data) {
                                isTapeRecordPermit = false;
                            }
                        });
                if (isTapeRecordPermit) {
                    pressCountDown.start();
                }
            }

            @Override
            public void actionUp(float upX) {
                tapeRecordIv.setEnabled(true);
                resetTapeRecordView();
                if (upX < LEFT_SLIDE_AVAILABLE_X) {
                    isTapeRecordStop = false;
                    RecordManager.getInstance().stop();

                } else {
                    isTapeRecordStop = true;
                    RecordManager.getInstance().stop();

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_conversation_chat_send:

                break;
        }
    }

    private void initRecyclerView() {
        rcvAdapter = new MessageChatRcvAdapter(this, msgList);
        layoutManager = new LinearLayoutManager(getAppContext());
        containerRcv.setLayoutManager(layoutManager);
        containerRcv.setAdapter(rcvAdapter);

        rcvAdapter.setOnMsgClickListener(new MessageChatRcvAdapter.OnMsgClickListener() {
            @Override
            public void voiceRecordClick(final int position) {
                Permissions.from(MainChatActivity.this, Permission.Group.STORAGE).start(new PermissionCallback() {
                    @Override
                    public void yes(List<String> data) {
                        if (msgList.get(position).getContentType() == ChatMsg.TYPE_CONTENT_TAPE_RECORD){
                            FileInfo resource = msgList.get(position).getResource();
                            if (resource == null) {
                                return;
                            }
                            String savePath = resource.getSavePath();
                            LogUtil.i("savePath", savePath);
                            if (FileUtil.isFileExists(savePath)){
                                playVoiceRecord(savePath, position);
                            }
                        }

                    }

                    @Override
                    public void no(List<String> data) {

                    }
                });
            }
        });

        rcvAdapter.setOnAvatarClickListener(new MessageChatRcvAdapter.OnAvatarClickListener() {
            @Override
            public void avatarClick(int position) {

            }
        });
    }

    //结束录音时重置界面
    private void resetTapeRecordView() {
        moreIv.setVisibility(View.VISIBLE);
        inputContainerFl.setVisibility(View.VISIBLE);
        recordPressIv.setVisibility(View.INVISIBLE);
        recordDeleteIv.setVisibility(View.INVISIBLE);
        voiceTimingTv.setVisibility(View.INVISIBLE);
        leftSlideIv.setVisibility(View.INVISIBLE);
        leftSlidePromptTv.setVisibility(View.INVISIBLE);

        tapeRecordSecond = 0;
        recordCountDown.cancel();
        pressCountDown.cancel();
        voiceTimingTv.setText(TimeUtil.formatTimeStr(0));

        AnimationDrawable animDrawable = (AnimationDrawable) leftSlideIv.getDrawable();
        if (animDrawable != null) {
            animDrawable.stop();
        }
    }


    //播放录音
    private void playVoiceRecord(String path, final int position) {
        TapeRecordManager.getInstance().playEndOrFail();
        boolean isPlaying = msgList.get(position).isPlaying();
        for (ChatMsg msg : msgList) {
            msg.setPlaying(false);
        }
        msgList.get(position).setPlaying(!isPlaying);
        updateRcv();
        if (msgList.get(position).isPlaying()) {
            TapeRecordManager.getInstance().playRecord(path, new android.media.MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(android.media.MediaPlayer mp) {
                    //播放完成
                    msgList.get(position).setPlaying(false);
                    updateRcv();
                    TapeRecordManager.getInstance().playEndOrFail();
                }
            });
        }
    }

    //刷新的同时，计算是否需要继续分页
    private void updateRcv() {
        if (rcvAdapter != null) {
            rcvAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onEventMsg(EventMsg msg) {
        if (msg.getKey() == EventMsg.SOCKET_ON_MESSAGE) {
            ChatMsg eventChatMsg = (ChatMsg) msg.getData();
            msgList.add(eventChatMsg);
            Collections.sort(msgList, new ChatMsg.TimeRiseComparator());
            updateRcv();

        } else if (msg.getKey() == EventMsg.MESSAGE_RECEIVE) {
            ChatMsg eventChatMsg = (ChatMsg) msg.getData();
            if (eventChatMsg != null) {
                String filePath = "";
                if (eventChatMsg.getResource() != null) {
                    filePath = eventChatMsg.getResource().getSavePath();
                }
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setFriendId(eventChatMsg.getFromid());
                friendInfo.setUsername(eventChatMsg.getUsername());
                IMUtil.getInstance().msgReceiveSaveDB(getAppContext(), eventChatMsg.getContent(),
                        eventChatMsg.getContentType(), 0, filePath, friendInfo, TransformUtil.ZH_CN);

                msgList.add(eventChatMsg);
                Collections.sort(msgList, new ChatMsg.TimeRiseComparator());
                updateRcv();
            }
        }
    }


}
