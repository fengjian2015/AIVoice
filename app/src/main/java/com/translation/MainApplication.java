package com.translation;

import android.app.Application;

import com.translation.androidlib.datamanager.EnDecryptUtil;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //加解密工具初始化
        EnDecryptUtil.init(getApplicationContext());
    }
}
