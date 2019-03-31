package com.translation.androidlib.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.util.ResourceUtil;
import java.io.File;

/**
 * 听写转换
 */
public class IatUilt {
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;//在线模式固定   离线模式SpeechConstant.TYPE_LOCAL
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 函数调用返回值
    int ret = 0;
    private Context context;

    public IatUilt(Context context){
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        // 初始化识别无UI识别对象
        mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Log.e("讯飞","初始化失败，错误码：" + code);
            }
        }
    };


    /**
     * 执行音频流识别操作
     * @param file 传入音频地址
     */
    public void executeStream(File file,String language,String accent,RecognizerListener mRecognizerListener) {
        // 设置参数
        setLatParam(language,accent);
        // 设置音频来源为外部文件
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        // 也可以像以下这样直接设置音频文件路径识别（要求设置文件在sdcard上的全路径）：
        // mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
        // mIat.setParameter(SpeechConstant.ASR_SOURCE_PATH, "sdcard/XXX/XXX.pcm");
        ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            Log.e("讯飞","识别失败,错误码：" + ret);
        } else {
            byte[] audioData = com.translation.util.FucUtil.readFile(file);
            if (null != audioData) {
                // 一次（也可以分多次）写入音频文件数据，数据格式必须是采样率为8KHz或16KHz（本地识别只支持16K采样率，云端都支持），位长16bit，单声道的wav或者pcm
                // 写入8KHz采样的音频时，必须先调用setParameter(SpeechConstant.SAMPLE_RATE, "8000")设置正确的采样率
                // 注：当音频过长，静音部分时长超过VAD_EOS将导致静音后面部分不能识别
                mIat.writeAudio(audioData, 0, audioData.length);
                mIat.stopListening();
            } else {
                mIat.cancel();
            }
        }
    }


    private String getResourcePath(){
        StringBuffer tempBuffer = new StringBuffer();
        //识别通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, "asr/common.jet"));
        tempBuffer.append(";");
        tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, "asr/sms.jet"));
        //识别8k资源-使用8k的时候请解开注释
        return tempBuffer.toString();
    }

    /**
     * 语音听写参数设置
     * @return
     */
    public void setLatParam(String language,String accent) {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        if (mEngineType.equals(SpeechConstant.TYPE_LOCAL)) {
            // 设置本地识别资源
            mIat.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
        }
        //设置语言，目前离线听写仅支持中文
        if (language.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, language);
            mIat.setParameter(SpeechConstant.ACCENT, accent);
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, language);
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, accent);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }
}
