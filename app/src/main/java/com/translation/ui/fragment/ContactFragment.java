package com.translation.ui.fragment;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;

import com.translation.R;
import com.translation.androidlib.manager.TapeRecordManager;
import com.translation.androidlib.utils.LogUtil;
import com.translation.component.base.BaseFragment;
import com.translation.component.permission.PermissionCallback;
import com.translation.component.permission.Permissions;
import com.yanzhenjie.permission.Permission;

import java.util.List;


public class ContactFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initViews() {
        Permissions.from(getHostActivity(), Permission.RECORD_AUDIO).start(new PermissionCallback() {
            @Override
            public void yes(List<String> data) {
                Button startBtn = parentView.findViewById(R.id.btn_start);
                startBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TapeRecordManager.getInstance().startRecord(getContext());
                    }
                });

                Button stopBtn = parentView.findViewById(R.id.btn_stop);
                stopBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TapeRecordManager.getInstance().stopRecord(new TapeRecordManager.OnVoiceListener() {
                            @Override
                            public void stop(String savePath) {
                                LogUtil.i("stop record savePath", savePath);
                                TapeRecordManager.getInstance().playRecord(savePath, new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        TapeRecordManager.getInstance().playEndOrFail();
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void no(List<String> data) {

            }
        });



    }
}
