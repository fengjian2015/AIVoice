package com.translation.ui.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.translation.R;
import com.translation.androidlib.utils.IMUtil;
import com.translation.androidlib.utils.ToastShow;
import com.translation.component.base.BaseFragment;
import com.translation.component.constant.SpCons;
import com.translation.ui.MainActivity;
import com.translation.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {

    }



    @OnClick({R.id.tv_more_personal_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more_personal_logout:
                ToastShow.showToastShowCenter(getHostActivity(),"退出中，请稍等");
                IMUtil.getInstance().logout(new MyEMCallBack());
                break;

        }
    }

    class MyEMCallBack implements EMCallBack {

        @Override
        public void onSuccess() {
            ToastShow.showToast2(getHostActivity(),"退出成功");
            SpCons.setLoginState(getHostActivity(), false);
            startActivity(new Intent(getHostActivity(), LoginActivity.class));
        }

        @Override
        public void onError(int code, String error) {
            ToastShow.showToast2(getHostActivity(),error);
        }

        @Override
        public void onProgress(int progress, String status) {

        }
    }

}
