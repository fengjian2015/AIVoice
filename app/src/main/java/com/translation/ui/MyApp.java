package com.translation.ui;

import android.app.Application;
import com.iflytek.cloud.SpeechUtility;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this, "appid=5c9a2bc0" );
        super.onCreate();
    }
}
