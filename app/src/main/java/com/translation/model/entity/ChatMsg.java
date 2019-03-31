package com.translation.model.entity;

import java.util.Comparator;

/**
 * Created by Darren on 2018/12/17.
 */
public class ChatMsg {

    public static final int TYPE_CONTENT_TEXT = 0;//文本类
    public static final int TYPE_CONTENT_VOICE = 1;//语音类
    public static final int TYPE_CONTENT_FILE = 2;//文件图片类
    public static final int TYPE_CONTENT_TAPE_RECORD = 3;//录音类

    private String msgId;
    private String selfId;
    private int contentType;//0文本类 1语音类 2文件图片类 3录音类
    private String content;
    private String fromid;
    private String username;
    private String avatar;
    private String groupId;
    private String groupname;
    private String groupavatar;
    private String groupnum;
    private String chatId;
    private String chatName;
    private String chatAvatar;
    private String msgType;
    private long timestamp;//消息的生成时间戳
    private long createTimestamp;//消息生成时间戳
    private String createTime;//消息生成时间
    private long topTimestamp;//置顶时间戳
    private String topTime;//置顶时间
    private int topMark;//0消息未置顶; 1消息已置顶
    private int shieldMark;//0未屏蔽; 1已屏蔽
    private boolean mine;
    private String userId;
    private String friendNote;
    private int offMsgNum;//离线消息数量
    private FileInfo resource;//文件
    private int isRead;//已读
    private int unread;//未读 0否；1是
    private int chatOpen;//聊天页是否打开; 0否；1是
    private boolean playing;//播放中
    private int show;//是否显示
    private boolean checked;//编辑状态是否选中
    private String language;//zh-cn中文；en英文


    public ChatMsg() {
    }

    public ChatMsg(int contentType, String content, String fromid, String username, String chatId, String chatName, long timestamp, boolean mine, String userId, String language) {
        this.contentType = contentType;
        this.content = content;
        this.fromid = fromid;
        this.username = username;
        this.chatId = chatId;
        this.chatName = chatName;
        this.timestamp = timestamp;
        this.mine = mine;
        this.userId = userId;
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupavatar() {
        return groupavatar;
    }

    public void setGroupavatar(String groupavatar) {
        this.groupavatar = groupavatar;
    }

    public String getGroupnum() {
        return groupnum;
    }

    public void setGroupnum(String groupnum) {
        this.groupnum = groupnum;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatAvatar() {
        return chatAvatar;
    }

    public void setChatAvatar(String chatAvatar) {
        this.chatAvatar = chatAvatar;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getTopTimestamp() {
        return topTimestamp;
    }

    public void setTopTimestamp(long topTimestamp) {
        this.topTimestamp = topTimestamp;
    }

    public String getTopTime() {
        return topTime;
    }

    public void setTopTime(String topTime) {
        this.topTime = topTime;
    }

    public int getTopMark() {
        return topMark;
    }

    public void setTopMark(int topMark) {
        this.topMark = topMark;
    }

    public int getShieldMark() {
        return shieldMark;
    }

    public void setShieldMark(int shieldMark) {
        this.shieldMark = shieldMark;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendNote() {
        return friendNote;
    }

    public void setFriendNote(String friendNote) {
        this.friendNote = friendNote;
    }

    public int getOffMsgNum() {
        return offMsgNum;
    }

    public void setOffMsgNum(int offMsgNum) {
        this.offMsgNum = offMsgNum;
    }

    public FileInfo getResource() {
        return resource;
    }

    public void setResource(FileInfo resource) {
        this.resource = resource;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public int getChatOpen() {
        return chatOpen;
    }

    public void setChatOpen(int chatOpen) {
        this.chatOpen = chatOpen;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    //按create时间戳正序排列
    public static class TimeRiseComparator implements Comparator<ChatMsg> {
        @Override
        public int compare(ChatMsg o1, ChatMsg o2) {
            if (o1.getTimestamp() - o2.getTimestamp() < 0) {
                return -1;
            } else if (o1.getTimestamp() - o2.getTimestamp() > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    //按置顶且时间倒序配列
    public static class TopComparator implements Comparator<ChatMsg> {
        @Override
        public int compare(ChatMsg o1, ChatMsg o2) {
            if (o1.getTopMark() == 1 && o2.getTopMark() != 1) {
                return -1;
            } else if (o1.getTopMark() != 1 && o2.getTopMark() == 1) {
                return 1;
            } else if (o1.getTimestamp() - o2.getTimestamp() > 0) {
                return -1;
            } else if (o1.getTimestamp() - o2.getTimestamp() < 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "msgId='" + msgId + '\'' +
                ", selfId='" + selfId + '\'' +
                ", contentType=" + contentType +
                ", content='" + content + '\'' +
                ", fromid='" + fromid + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupname='" + groupname + '\'' +
                ", groupavatar='" + groupavatar + '\'' +
                ", groupnum='" + groupnum + '\'' +
                ", chatId='" + chatId + '\'' +
                ", chatName='" + chatName + '\'' +
                ", chatAvatar='" + chatAvatar + '\'' +
                ", msgType='" + msgType + '\'' +
                ", timestamp=" + timestamp +
                ", createTimestamp=" + createTimestamp +
                ", createTime='" + createTime + '\'' +
                ", topTimestamp=" + topTimestamp +
                ", topTime='" + topTime + '\'' +
                ", topMark=" + topMark +
                ", shieldMark=" + shieldMark +
                ", mine=" + mine +
                ", userId='" + userId + '\'' +
                ", friendNote='" + friendNote + '\'' +
                ", offMsgNum=" + offMsgNum +
                ", resource=" + resource +
                ", isRead=" + isRead +
                ", unread=" + unread +
                ", chatOpen=" + chatOpen +
                ", playing=" + playing +
                ", show=" + show +
                ", checked=" + checked +
                ", language=" + language +
                '}';
    }
}
