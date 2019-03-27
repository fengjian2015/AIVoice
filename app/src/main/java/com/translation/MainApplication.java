package com.translation;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.translation.androidlib.datamanager.EnDecryptUtil;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, "appid=5c9a2bc0" );
        //加解密工具初始化
        EnDecryptUtil.init(getApplicationContext());
        SpeechUtility.createUtility(this, "appid=dsadasdasdasd" );
        //加解密工具初始化
        EnDecryptUtil.init(getApplicationContext());
    }
}
