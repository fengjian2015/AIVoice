package com.translation.androidlib.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * 语音合成
 */
public class TtsUtil {
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;//在线模式固定   离线模式SpeechConstant.TYPE_LOCAL
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer = "catherine";
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;

    private Context context;
    //文本显示
    String texts = "";

    //记录合成语音地址
    private String file_content;

    public TtsUtil(Context context){
        this.context=context;
        init();
    }
    private void init() {
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
    }

    /**
     * 合成语音
     * @param text
     */
    public void ttsSynthesis(String text,SynthesizerListener mTtsListener){
        setTtsParam();
        this.texts=text;
        int code = mTts.startSpeaking(texts, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            Log.e("讯飞","语音合成失败,错误码: " + code);
        }
    }

    /**
     * 获取合成文件地址
     * @return
     */
    public String getFile_content(){
        return file_content;
    }


    public void  pauseSpeaking(){
        mTts.pauseSpeaking();
        mTts.stopSpeaking();
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Log.e("讯飞", "初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };




    /**
     * 合成语音参数设置
     *
     * @return
     */
    private void setTtsParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //支持实时音频返回，仅在synthesizeToUri条件下支持
            //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");

        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        String file = Environment.getExternalStorageDirectory() + "/msc/" + System.currentTimeMillis() + ".wav";
        file_content=file;
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, file);
    }
}
