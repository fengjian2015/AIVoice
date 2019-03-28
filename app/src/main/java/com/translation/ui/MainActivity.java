package com.translation.ui;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import com.translation.R;
import com.translation.androidlib.utils.AmrFileDecoder;
import com.translation.androidlib.utils.FileUtil;
import com.translation.androidlib.utils.LogUtil;
import com.translation.androidlib.utils.TransformUtil;
import com.translation.component.base.BaseActivity;
import com.translation.component.permission.PermissionCallback;
import com.translation.component.permission.Permissions;
import com.translation.ui.adapter.MainAdapter;
import com.translation.ui.fragment.ContactFragment;
import com.translation.ui.fragment.ConversationFragment;
import com.translation.ui.fragment.MineFragment;
import com.translation.ui.widget.CustomScrollViewPager;
import com.yanzhenjie.permission.Permission;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    CustomScrollViewPager mViewpager;
    @BindView(R.id.ll_main_bot_container)
    LinearLayout llMenu;
    private MainAdapter adapter;
    private List<Fragment> fragmentList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsData() {
        setTitle(false, 0);
        ButterKnife.bind(this);
        initPage();
        initMenu();

        Permissions.from(this, Permission.Group.STORAGE).start(new PermissionCallback() {
            @Override
            public void yes(List<String> data) {
                String dirPath = FileUtil.getSDDirPath(getAppContext());
//                String filePath = dirPath + "voiceRecord/20190327_230742_voiceRecord.amr";
                String filePath = dirPath + "df39222d-ca4c-45b6-a7b2-21939ccae15e.wav";


                LogUtil.i("filePath", filePath);
                TransformUtil transformUtil = new TransformUtil(getAppContext());
                transformUtil.setOnTransformListener(new TransformUtil.OnTransformListener() {
                    @Override
                    public void onTransform(String results, String fileString) {
                        LogUtil.i("transform results", results);
                    }
                });
                transformUtil.VoiceToText(new File(filePath), TransformUtil.EN);


                try {
                    FileInputStream inputStream = new FileInputStream(filePath);
                    String outPath =  AmrFileDecoder.amrToWav(inputStream, dirPath);
                    LogUtil.i("outPath", outPath);

                }catch (Exception e){
                    e.printStackTrace();
                }


                /*TapeRecordManager.getInstance().playRecord(filePath, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        TapeRecordManager.getInstance().playEndOrFail();
                    }
                });*/

            }

            @Override
            public void no(List<String> data) {

            }
        });

    }

    private void initMenu() {
        for (int i = 0; i < llMenu.getChildCount(); i++) {
            final int finalI = i;
            llMenu.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewpager.setCurrentItem(finalI, false);
                    selectFragment(finalI);
                }
            });
        }
    }

    private void selectFragment(int po) {
        for (int i = 0; i < llMenu.getChildCount(); i++) {
            llMenu.getChildAt(i).setSelected(false);
        }
        llMenu.getChildAt(po).setSelected(true);
    }

    private void initPage() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ConversationFragment());
        fragmentList.add(new ContactFragment());
        fragmentList.add(new MineFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), fragmentList);
        mViewpager.setAdapter(adapter);
        selectFragment(0);
    }

}
