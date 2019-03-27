package com.translation.ui.activity;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import com.translation.R;
import com.translation.androidlib.datamanager.DataCache;
import com.translation.androidlib.datamanager.SpCache;
import com.translation.androidlib.observer.EventManager;
import com.translation.androidlib.observer.EventMsg;
import com.translation.androidlib.utils.ToastShow;
import com.translation.androidlib.utils.ToastUtil;
import com.translation.component.base.BaseActivity;
import com.translation.component.constant.SpCons;
import com.translation.model.entity.LoginUser;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 手机号码注册
 * Created by Darren on 2018/12/14.
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.et_register_username)
    EditText usernameEt;
    @BindView(R.id.et_register_email)
    EditText emailEt;
    @BindView(R.id.et_register_phone_num)
    EditText phoneNumEt;
    @BindView(R.id.et_login_pwd)
    EditText passwordEt;
    @BindView(R.id.btn_register)
    Button registerBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void initViewsData() {
        setTitle(true, "AIVoice注册");
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        String username = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String phoneNum = phoneNumEt.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showShort(getAppContext(), "信息输入不完整");

        } else {
            LoginUser loginUser = new LoginUser();
            loginUser.setEmail(email);
            loginUser.setPhoneNum(phoneNum);
            DataCache spCache = new SpCache(getAppContext());
            spCache.setObject(SpCons.SP_KEY_LOGIN_USER, loginUser);
            getHostActivity().finish();
            EventManager.post(new EventMsg(EventMsg.REGISTER_FINISH, username, password));
        }

    }


}
