package com.translation.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.translation.R;
import com.translation.androidlib.datamanager.DataCache;
import com.translation.androidlib.datamanager.SpCache;
import com.translation.androidlib.utils.AmrFileDecoder;
import com.translation.androidlib.utils.FileUtil;
import com.translation.androidlib.utils.LogUtil;
import com.translation.androidlib.utils.ToastShow;
import com.translation.androidlib.utils.TransformUtil;
import com.translation.androidlib.utils.IMUtil;
import com.translation.component.base.BaseActivity;
import com.translation.component.constant.SpCons;
import com.translation.component.permission.PermissionCallback;
import com.translation.component.permission.Permissions;
import com.translation.model.db.dao.UserDao;
import com.translation.model.entity.LoginUser;
import com.translation.ui.activity.LoginActivity;
import com.translation.ui.adapter.MainAdapter;
import com.translation.ui.fragment.ContactFragment;
import com.translation.ui.fragment.ConversationFragment;
import com.translation.ui.fragment.MineFragment;
import com.translation.ui.widget.CustomScrollViewPager;
import com.yanzhenjie.permission.Permission;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    CustomScrollViewPager mViewpager;
    @BindView(R.id.ll_main_bot_container)
    LinearLayout llMenu;
    private MainAdapter adapter;
    private List<Fragment> fragmentList;
    private long exitTime = 0;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsData() {
        UserDao.user = UserDao.getUser(getAppContext());
        setTitle(false, 0);

        ButterKnife.bind(this);
        initPage();
        initMenu();
        initIM();
    }

    private void initIM() {
        IMUtil.getInstance().setMyConnectionListener(new MyConnectionListener());
        IMUtil.getInstance().login(UserDao.user.getUsername(), UserDao.user.getPassword(), new MyEMCallBack());
    }


    class MyEMCallBack implements EMCallBack {
        @Override
        public void onSuccess() {
            ToastShow.showToast2(getHostActivity(), "登陆成功");

        }

        @Override
        public void onError(int code, String error) {
            if (code == 200) {
                IMUtil.getInstance().init();
            } else {
                ToastShow.showToast2(getHostActivity(), error);
            }
        }

        @Override
        public void onProgress(int progress, String status) {

        }
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


    /**
     * 链接状态监听
     */
    //实现ConnectionListener接口
    public class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            LogUtil.d("fengjian", "onConnected");
        }

        @Override
        public void onDisconnected(final int error) {
            LogUtil.d("fengjian", "'" + error);
            if (error == EMError.USER_REMOVED) {
                // 显示帐号已经被移除
            } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                // 显示帐号在其他设备登录
            } else {
//                        if (NetUtils.hasNetwork(MainActivity.this))
                //连接不到聊天服务器
//                        else
                //当前网络不可用，请检查网络设置
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
