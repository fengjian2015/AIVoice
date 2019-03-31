package com.translation;

import android.app.Application;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.iflytek.cloud.SpeechUtility;
import com.translation.androidlib.datamanager.EnDecryptUtil;
import com.translation.androidlib.utils.AppUtil;
import com.translation.androidlib.utils.FileUtil;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, "appid=5c9a2bc0");
        //加解密工具初始化
        EnDecryptUtil.init(getApplicationContext());
        initIM();
        //初始化录音
        RecordManager.getInstance().init(this, true);
        RecordManager.getInstance().changeRecordDir(FileUtil.getSDVoiceRecordPath(getApplicationContext()));
        RecordManager.getInstance().changeFormat(RecordConfig.RecordFormat.WAV);
    }

    private void initIM() {
        int pid = android.os.Process.myPid();
        String processAppName = AppUtil.getAppName(pid,this);
    // 如果APP启用了远程的service，此application:onCreate会被调用2次
    // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
    // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e("fengjian", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
}
