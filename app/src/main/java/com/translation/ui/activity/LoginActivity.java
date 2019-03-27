package com.translation.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.translation.R;
import com.translation.androidlib.datamanager.DataCache;
import com.translation.androidlib.datamanager.SpCache;
import com.translation.androidlib.observer.EventMsg;
import com.translation.androidlib.utils.ToastShow;
import com.translation.androidlib.utils.ToastUtil;
import com.translation.component.base.BaseActivity;
import com.translation.component.constant.SpCons;
import com.translation.model.entity.LoginUser;
import com.translation.ui.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手机号登录
 * Created by Darren on 2018/12/14.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_username)
    EditText usernameEt;
    @BindView(R.id.et_login_pwd)
    EditText pwdEt;
    @BindView(R.id.btn_login)
    Button loginBtn;
    @BindView(R.id.btn_register)
    Button registerBtn;
    @BindView(R.id.tv_login_find_back_pwd)
    TextView findBackTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsData() {
        setTitle(false, 0);
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = usernameEt.getText().toString().trim();
                String password = pwdEt.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ToastUtil.showShort(getAppContext(), "请输入用户名和密码");
                } else if (("aaaaa".equals(username) && "11111".equals(password))
                        || ("bbbbb".equals(username) && "11111".equals(password))) {
                    startActivity(new Intent(getAppContext(), MainActivity.class));
                    SpCons.setLoginState(getAppContext(), true);
                    DataCache spCache = new SpCache(getAppContext());
                    LoginUser loginUser = (LoginUser) spCache.getObject(SpCons.SP_KEY_LOGIN_USER);
                    if (loginUser == null){
                        loginUser = new LoginUser();
                    }
                    loginUser.setUsername(username);
                    spCache.setObject(SpCons.SP_KEY_LOGIN_USER, loginUser);
                }
                break;

            case R.id.btn_register:
                startActivity(new Intent(getAppContext(), RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onEventMsg(EventMsg msg) {
        if (msg.getKey() == EventMsg.REGISTER_FINISH){
            String username = msg.getData().toString();
            String password = msg.getExtraData().toString();
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                usernameEt.setText(username);
                pwdEt.setText(password);
            }
        }
    }


}
