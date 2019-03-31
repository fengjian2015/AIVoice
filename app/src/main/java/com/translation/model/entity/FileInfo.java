package com.translation.model.entity;

/**
 * Created by Darren on 2019/1/5.
 */
public class FileInfo {

    private String id;
    private String fileName;
    //文件原始路径
    private String originPath;
    //服务器下载链接
    private String downloadPath;
    //文件下载后保存在本地路径
    private String savePath;
    private long fileLength;
    private int fileType;
    private long createTime;
    private String fileMd5;
    private boolean checked;
    private String album;
    private String artist;
    private long duration;


    public FileInfo() {
    }

    public FileInfo(long createTime, long fileLength, String fileMd5, String fileName, int fileType, String id, String downloadPath) {
        this.createTime = createTime;
        this.fileLength = fileLength;
        this.fileMd5 = fileMd5;
        this.fileName = fileName;
        this.fileType = fileType;
        this.id = id;
        this.downloadPath = downloadPath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", originPath='" + originPath + '\'' +
                ", downloadPath='" + downloadPath + '\'' +
                ", savePath='" + savePath + '\'' +
                ", fileLength=" + fileLength +
                ", fileType=" + fileType +
                ", createTime='" + createTime + '\'' +
                ", fileMd5='" + fileMd5 + '\'' +
                ", checked=" + checked +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                '}';
    }
}
