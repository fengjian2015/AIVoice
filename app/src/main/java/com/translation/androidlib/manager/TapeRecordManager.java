package com.translation.androidlib.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.translation.androidlib.utils.FileUtil;
import com.translation.androidlib.utils.TimeUtil;

import java.io.File;
import java.io.IOException;

/**
 * 录音
 * Created by Darren on 2019/2/2
 */
public class TapeRecordManager {

    private static TapeRecordManager instance;
    private String currentVoicePath;
    private MediaRecorder mediaRecorder;
    private boolean isPrepare;
    private MediaPlayer mediaPlayer;

    public interface OnVoiceListener{
        void stop(String savePath);
    }

    public static TapeRecordManager getInstance() {
        if (instance == null) {
            synchronized (TapeRecordManager.class) {
                if (instance == null) {
                    instance = new TapeRecordManager();
                }
            }
        }
        return instance;
    }

    public void startRecord(Context context) {
        try {
            isPrepare = false;
            String savePath = FileUtil.getSDVoiceRecordPath(context);
            FileUtil.createDir(savePath);
            String filename = TimeUtil.getCurrentTime("yyyyMMdd_HHmmss") + "_voiceRecord.amr";
            File file = new File(savePath, filename);
            currentVoicePath = file.getAbsolutePath();

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setOutputFile(currentVoicePath);// 设置输出文件
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置MediaRecorder的音频源为麦克风
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);// 设置音频格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置音频编码
            mediaRecorder.setMaxDuration(60 * 1000);// 时长上限
            mediaRecorder.prepare();// 准备录音
            mediaRecorder.start();// 开始

            isPrepare = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord(OnVoiceListener voiceListener) {
        if (mediaRecorder == null){
            return;
        }
        //5.0以上在调用stop的时候会报错，捕获异常清理
        try {
            release();
            if (voiceListener != null){
                voiceListener.stop(currentVoicePath);
            }
            currentVoicePath = null;

        } catch (RuntimeException e) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            /*File file = new File(currentVoicePath);
            if (file.exists()){
                file.delete();
                currentVoicePath = null;
            }*/
        }
    }

    public void playRecord(String path, MediaPlayer.OnCompletionListener completionListener){
        try {
            //初始化播放器
            mediaPlayer = new MediaPlayer();
            //设置播放音频数据文件
            mediaPlayer.setDataSource(path);
            //设置播放监听事件
            if (completionListener != null){
                mediaPlayer.setOnCompletionListener(completionListener);
            }
            //播放发生错误监听事件
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    playEndOrFail();
                    return true;
                }
            });
            //播放器音量配置
            mediaPlayer.setVolume(1, 1);
            //是否循环播放
            mediaPlayer.setLooping(false);
            //准备及播放
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
            //播放失败处理
            playEndOrFail();
        }
    }

    /**
     * 停止播放或播放失败处理
     */
    public void playEndOrFail() {
        if (null != mediaPlayer) {
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 释放资源
     */
    private void release() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    /**
     * 取消录音
     */
    public void cancelRecord() {
        release();
        if (currentVoicePath != null) {
            File file = new File(currentVoicePath);
            file.delete();
            currentVoicePath = null;
        }
    }

    public int getVoiceLevel(int maxLevel) {
        if (isPrepare) {
            try {
                return maxLevel * mediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

}
