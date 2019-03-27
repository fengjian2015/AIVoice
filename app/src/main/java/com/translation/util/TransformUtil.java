package com.translation.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

import java.io.File;

public class TransformUtil {
    //语音转文本，翻译
    public static final int VOICETOTEXT=1;
    //语音转翻译后的语音
    public static final int VOICE_TO_VOICE=2;

    public static final String EN="en";
    public static final String ZH_CN="zh-cn";

    //语音合成
    private TtsUtil ttsUtil;
    //听写转换
    private IatUilt iatUilt;

    private Context context;
    //类型
    private int type=0;
    //目标语音 en   zh-cn
    private String language="en";

    //语音转换的文本内容
    private String iatText;


    public TransformUtil(Context context){
        this.context=context;
        ttsUtil=new TtsUtil(context);
        iatUilt=new IatUilt(context);
    }

    /**
     * 语音转文本并且翻译
     * @param file
     * @param language
     */
    public void VoiceToText(File file,String language){
        this.language=language;
        type=VOICETOTEXT;
        iatUilt.executeStream(file,mRecognizerListener);
    }

    /**
     * 语音转翻译后的语音
     * @param file
     * @param language
     */
    public void VoiceToVoice(File file,String language){
        this.language=language;
        type=VOICE_TO_VOICE;
        iatUilt.executeStream(file,mRecognizerListener);
    }


    /**
     * 翻译
     * @param text
     */
    public void translate(String text){
        new TranslateUtil().translate(context, "auto", language, text, translateCallback);
    }

    /**
     * 翻译
     */
    TranslateUtil.TranslateCallback translateCallback = new TranslateUtil.TranslateCallback() {
        @Override
        public void onTranslateDone(String result) {
            // result是翻译结果，在这里使用翻译结果，比如使用对话框显示翻译结果
            if(type==VOICETOTEXT){
                // TODO: 2019/3/27 通过Evenbus回传
            }else if(type==VOICE_TO_VOICE){
                ttsUtil.ttsSynthesis(result,mTtsListener);
            }
        }
    };


    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
//            mPercentForBuffering = percent;

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
//            mPercentForPlaying = percent;
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                // TODO: 2019/3/27 完成操作
                if(type==VOICE_TO_VOICE){
                    // TODO: 2019/3/27 通过Evenbus回传文件地址
                    ttsUtil.getFile_content();
                }
            } else if (error != null) {
                Log.e("讯飞",error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            Log.e("讯飞",error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            iatText = JsonParser.parseIatResult(results.getResultString());
            Log.e("讯飞","结果："+iatText);
            if (isLast) {
                //TODO 最后的结果
                if(type==VOICETOTEXT||type==VOICE_TO_VOICE){
                    translate(iatText);
                }
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

}