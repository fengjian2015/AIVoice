package com.translation.ui.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.translation.R;
import com.translation.androidlib.manager.TapeRecordManager;
import com.translation.androidlib.utils.LogUtil;
import com.translation.component.base.BaseFragment;
import com.translation.component.base.BaseRcvAdapter;
import com.translation.component.constant.ContactCons;
import com.translation.component.constant.SpCons;
import com.translation.component.permission.PermissionCallback;
import com.translation.component.permission.Permissions;
import com.translation.model.entity.FriendInfo;
import com.translation.ui.activity.MainChatActivity;
import com.translation.ui.adapter.ContactRcvAdapter;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ContactFragment extends BaseFragment {

    private RecyclerView containerRcv;
    private TextView changeLanguageTv;

    private List<FriendInfo> friendInfoList = new ArrayList<>();
    private ContactRcvAdapter rcvAdapter;

    private boolean isZH_CN = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initViews() {
        containerRcv = parentView.findViewById(R.id.rcv_contact_container);
        changeLanguageTv = parentView.findViewById(R.id.tv_contact_change_language);
    }

    @Override
    protected void initViewsData() {
        initRecyclerView();
        friendInfoList.clear();
        friendInfoList.addAll(getTestData());
        updateRcv();

        String currentLanguage = SpCons.getCurrentLanguage(getHostActivity());
        if ("ZH_CN".equals(currentLanguage)){

        }

    }

    @Override
    protected void setListener() {
        changeLanguageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initRecyclerView(){
        rcvAdapter = new ContactRcvAdapter(getHostActivity(), friendInfoList);
        containerRcv.setLayoutManager(new LinearLayoutManager(getHostActivity()));
        containerRcv.setAdapter(rcvAdapter);

        rcvAdapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                LogUtil.i("itemClick position", position);
                Intent intent = new Intent(getHostActivity(), MainChatActivity.class);
                intent.putExtra(ContactCons.CONTACT_FRIEND, friendInfoList.get(position));
                startActivity(intent);
            }
        });
    }

    private void updateRcv(){
        if (rcvAdapter != null){
            rcvAdapter.notifyDataSetChanged();
        }
    }

    private List<FriendInfo> getTestData(){
        List<FriendInfo> friendInfoList = new ArrayList<>();
        friendInfoList.add(new FriendInfo("1", "张三a", "aaaaa"));
        friendInfoList.add(new FriendInfo("2", "李四b", "bbbbb"));
        return friendInfoList;
    }



    /*private void testTapeRecord(){
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
*/


}
