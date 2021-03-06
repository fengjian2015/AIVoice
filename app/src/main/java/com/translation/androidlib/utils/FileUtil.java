package com.translation.androidlib.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    private static final int FLAG_SUCCESS = 1;//创建成功
    private static final int FLAG_EXISTS = 2;//已存在
    private static final int FLAG_FAILED = 3;//创建失败
    private static final String DIR_NAME_MAIN = "AIVoice/";
    private static final String DIR_NAME_IMG = "images/";
    private static final String DIR_NAME_DOWNLOAD = "download/";
    //录音文件夹名
    private static final String DIR_NAME_VOICE_RECORD = "voiceRecord/";
    //声明各种类型文件的dataType
    private static final String DATA_TYPE_APK = "application/vnd.android.package-archive";
    private static final String DATA_TYPE_VIDEO = "video/*";
    private static final String DATA_TYPE_AUDIO = "audio/*";
    private static final String DATA_TYPE_HTML = "text/html";
    private static final String DATA_TYPE_IMAGE = "image/*";
    private static final String DATA_TYPE_PPT = "application/vnd.ms-powerpoint";
    private static final String DATA_TYPE_EXCEL = "application/vnd.ms-excel";
    private static final String DATA_TYPE_WORD = "application/msword";
    private static final String DATA_TYPE_CHM = "application/x-chm";
    private static final String DATA_TYPE_TXT = "text/plain";
    private static final String DATA_TYPE_PDF = "application/pdf";
    //未指定明确的文件类型，不能使用精确类型的工具打开，需要用户选择
    private static final String DATA_TYPE_ALL = "*/*";


    /**
     * 内部存储中的files路径
     */
    public static String getInnerFilePath(Context context) {
        String internalFilePath = context.getFilesDir().getAbsolutePath();
        if (!internalFilePath.endsWith(File.separator)) {
            return internalFilePath + File.separator;
        }
        return internalFilePath;
    }

    /**
     * 内部存储中的cache路径
     */
    public static String getInnerCachePath(Context context) {
        String innerCachePath = context.getCacheDir().getAbsolutePath();
        if (!innerCachePath.endsWith(File.separator)) {
            return innerCachePath + File.separator;
        }
        return innerCachePath;
    }

    /**
     * SD卡中的files路径,无SD卡则返回对应内部路径
     */
    public static String getSDFilePath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = context.getExternalFilesDir("");
            if (file != null) {
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(File.separator)){
                    return filePath + File.separator;
                }
                return filePath;
            }
        }
        return getInnerFilePath(context);
    }

    /**
     * SD卡中的cache路径,无SD卡则返回对应内部路径
     */
    public static String getSDCachePath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = context.getExternalCacheDir();
            if (file != null) {
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(File.separator)){
                    return filePath + File.separator;
                }
                return filePath;
            }
        }
        return getInnerCachePath(context);
    }

    /**
     * SD卡的根路径,无SD卡则返回对应内部路径
     */
    public static String getSDDirPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (!TextUtils.isEmpty(path)) {
                if (!path.endsWith(File.separator)) {
                    return path + File.separator + DIR_NAME_MAIN;
                }
                return path + DIR_NAME_MAIN;
            } else {
                File file = Environment.getExternalStoragePublicDirectory("");
                if (file != null){
                    String externalPath = file.getAbsolutePath();
                    if (!externalPath.endsWith(File.separator)) {
                        return externalPath + File.separator + DIR_NAME_MAIN;
                    }
                    return externalPath + DIR_NAME_MAIN;
                }
            }
        }
        return getInnerFilePath(context);
    }

    /**
     * SD卡图片路径
     */
    public static String getSDImgPath(Context context){
        return getSDDirPath(context) + DIR_NAME_IMG;
    }

    /**
     * SD卡下载路径
     */
    public static String getSDDownloadPath(Context context){
        return getSDDirPath(context) + DIR_NAME_DOWNLOAD;
    }

    /**
     * SD卡录音路径
     */
    public static String getSDVoiceRecordPath(Context context){
        return getSDDirPath(context) + DIR_NAME_VOICE_RECORD;
    }

    /**
     * 删除文件夹及其下的文件
     */
    public static void deleteDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir(dirPath);
        }
        dir.delete();
    }

    /**
     * 删除sd卡图片文件夹
     */
    public static void deleteImgDir(Context context) {
        deleteDir(getSDImgPath(context));
    }

    public static boolean isFileExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建 单个 文件
     *
     * @param filePath 待创建的文件路径
     * @return 结果码
     */
    public static int createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            LogUtil.i("The directory [ " + filePath + " ] has already exists");
            return FLAG_EXISTS;
        }
        if (filePath.endsWith(File.separator)) {// 以 路径分隔符 结束，说明是文件夹
            LogUtil.i("The file [ \" + filePath + \" ] can not be a directory");
            return FLAG_FAILED;
        }

        //判断父目录是否存在
        if (!file.getParentFile().exists()) {
            //父目录不存在 创建父目录
            LogUtil.i("creating parent directory...");
            if (!file.getParentFile().mkdirs()) {
                LogUtil.i("created parent directory failed...");
                return FLAG_FAILED;
            }
        }

        //创建目标文件
        try {
            if (file.createNewFile()) {//创建文件成功
                LogUtil.i("create file [ " + filePath + " ] success");
                return FLAG_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i("create file [ " + filePath + " ] failed");
            return FLAG_FAILED;
        }

        return FLAG_FAILED;
    }

    /**
     * 创建文件夹
     *
     * @param dirPath 文件夹路径
     * @return 结果码
     */
    public static int createDir(String dirPath) {
        File dir = new File(dirPath);
        //文件夹是否已经存在
        if (dir.exists()) {
            LogUtil.i("The directory [ " + dirPath + " ] has already exists");
            return FLAG_EXISTS;
        }
        if (!dirPath.endsWith(File.separator)) {//不是以路径分隔符"/"结束，则添加路径分隔符"/"
            dirPath = dirPath + File.separator;
        }
        //创建文件夹
        if (dir.mkdirs()) {
            LogUtil.i("create directory [ " + dirPath + " ] success");
            return FLAG_SUCCESS;
        }
        LogUtil.i("create directory [ " + dirPath + " ] failed");
        return FLAG_FAILED;
    }

    /**
     * 打开文件
     */
    public static void openFile(Context mContext, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        // 取得文件扩展名
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1,
                file.getName().length()).toLowerCase();
        // 依扩展名的类型决定MimeType
        switch (end) {
            case "3gp":
            case "mp4":
                openVideoFile(mContext, file);
                break;
            case "m4a":
            case "mp3":
            case "mid":
            case "xmf":
            case "ogg":
            case "wav":
                openAudioFile(mContext, file);
                break;
            case "doc":
            case "docx":
                openCommonFile(mContext, file, DATA_TYPE_WORD);
                break;
            case "xls":
            case "xlsx":
                openCommonFile(mContext, file, DATA_TYPE_EXCEL);
                break;
            case "jpg":
            case "gif":
            case "png":
            case "jpeg":
            case "bmp":
                openCommonFile(mContext, file, DATA_TYPE_IMAGE);
                break;
            case "txt":
                openCommonFile(mContext, file, DATA_TYPE_TXT);
                break;
            case "htm":
            case "html":
                openCommonFile(mContext, file, DATA_TYPE_HTML);
                break;
            case "apk":
                openCommonFile(mContext, file, DATA_TYPE_APK);
                break;
            case "ppt":
                openCommonFile(mContext, file, DATA_TYPE_PPT);
                break;
            case "pdf":
                openCommonFile(mContext, file, DATA_TYPE_PDF);
                break;
            case "chm":
                openCommonFile(mContext, file, DATA_TYPE_CHM);
                break;
            default:
                openCommonFile(mContext, file, DATA_TYPE_ALL);
                break;
        }
    }

    /**
     * 传入type打开文件
     */
    private static void openCommonFile(Context mContext, File file, String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        FileProviderUtil.setIntentDataAndType(mContext, intent, type, file, true);
        mContext.startActivity(intent);
    }

    /**
     * 打开Video文件
     */
    private static void openVideoFile(Context mContext, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        FileProviderUtil.setIntentDataAndType(mContext, intent, DATA_TYPE_VIDEO, file, false);
        mContext.startActivity(intent);
    }

    /**
     * 打开Audio文件
     */
    private static void openAudioFile(Context mContext, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        FileProviderUtil.setIntentDataAndType(mContext, intent, DATA_TYPE_AUDIO, file, false);
        mContext.startActivity(intent);
    }

}
