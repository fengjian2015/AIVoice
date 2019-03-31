package com.translation.androidlib.utils;

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
    public static final int VOICE_TO_TEXT = 1;
    //语音转翻译后的语音
    public static final int VOICE_TO_VOICE = 2;
    //文本转语音
    public static final int TEXT_TO_VOICE = 3;
    //文本转语音并且翻译
    public static final int TEXT_TO_VOICE_TRANSLATION = 4;

    public static final String EN = "en";
    public static final String ZH_CN = "zh-cn";

    //语音合成
    private TtsUtil ttsUtil;
    //听写转换
    private IatUilt iatUilt;

    private Context context;
    //类型
    private int type = 0;
    //目标语音 en   zh-cn
    private String accent = "en_us";
    private String language = "zh-cn";

    //语音转换的文本内容
    private StringBuilder iatTextSb = new StringBuilder();
    private OnTransformListener onTransformListener;


    public TransformUtil(Context context) {
        this.context = context;
        ttsUtil = new TtsUtil(context);
        iatUilt = new IatUilt(context);
    }


    /**
     * 文字转语音
     *
     * @param content
     * @param accent
     */
    public void textToVoice(String content, String language, String accent) {
        iatTextSb.delete(0, iatTextSb.length());
        this.accent = accent;
        this.language = language;
        type = TEXT_TO_VOICE;
        ttsUtil.ttsSynthesis(content, mTtsListener);
    }

    /**
     * 文字转语音并且翻译
     *
     * @param content
     * @param accent
     */
    public void textToVoicetTranslation(String content, String language, String accent) {
        iatTextSb.delete(0, iatTextSb.length());
        this.accent = accent;
        this.language = language;
        type = TEXT_TO_VOICE_TRANSLATION;
        translate(content);
    }


    /**
     * 语音转文本并且
     *
     * @param file
     * @param accent
     */
    public void voiceToText(File file, String language, String accent) {
        iatTextSb.delete(0, iatTextSb.length());
        this.accent = accent;
        this.language = language;
        type = VOICE_TO_TEXT;
        iatUilt.executeStream(file, language, accent, mRecognizerListener);
    }


    /**
     * 语音转翻译后的语音
     *
     * @param file
     * @param accent
     */
    public void voiceToVoice(File file, String language, String accent) {
        iatTextSb.delete(0, iatTextSb.length());
        this.accent = accent;
        this.language = language;
        type = VOICE_TO_VOICE;
        iatUilt.executeStream(file, language, accent, mRecognizerListener);
    }

    public void setOnTransformListener(OnTransformListener onTransformListener) {
        this.onTransformListener = onTransformListener;
    }

    /**
     * 翻译
     */
    public void translate(String text) {
        new TranslateUtil().translate(context, "auto", accent, text, translateCallback);
    }

    /**
     * 翻译
     */
    TranslateUtil.TranslateCallback translateCallback = new TranslateUtil.TranslateCallback() {
        @Override
        public void onTranslateDone(String result) {
            // result是翻译结果，在这里使用翻译结果，比如使用对话框显示翻译结果
            if (type == VOICE_TO_TEXT) {
                if (onTransformListener != null)
                    onTransformListener.onTransform(result, "", type);
            } else if (type == VOICE_TO_VOICE || type == TEXT_TO_VOICE_TRANSLATION || type == TEXT_TO_VOICE) {
                ttsUtil.ttsSynthesis(result, mTtsListener);
            }
        }
    };


    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            ttsUtil.pauseSpeaking();
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            // 合成进度
//            mPercentForBuffering = percent;
            if (percent == 100) {
                // TODO: 2019/3/27 完成操作
                if (type == VOICE_TO_VOICE || type == TEXT_TO_VOICE || type == TEXT_TO_VOICE_TRANSLATION) {
                    if (onTransformListener != null) {
                        onTransformListener.onTransform("", ttsUtil.getFile_content(), type);
                    }
                }
            }
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
//            mPercentForPlaying = percent;
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error != null) {
                Log.e("讯飞", error.getPlainDescription(true));
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
            Log.e("讯飞", error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            LogUtil.i("听写监听器", results);
            iatTextSb.append(JsonParser.parseIatResult(results.getResultString()));
//            Log.e("讯飞", "结果：" + iatText);
            if (isLast) {
                //TODO 最后的结果
                if (type == VOICE_TO_TEXT || type == VOICE_TO_VOICE) {
                    translate(iatTextSb.toString());
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

    public interface OnTransformListener {
        void onTransform(String results, String fileString, int type);
    }
}
