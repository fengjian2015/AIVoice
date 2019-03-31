package com.translation.androidlib.utils;

import android.content.Context;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.translation.androidlib.observer.EventManager;
import com.translation.androidlib.observer.EventMsg;
import com.translation.model.db.dao.MessageDao;
import com.translation.model.db.dao.UserDao;
import com.translation.model.entity.ChatMsg;
import com.translation.model.entity.FileInfo;
import com.translation.model.entity.FriendInfo;
import com.translation.model.entity.LoginUser;

import java.io.File;
import java.util.List;

public class IMUtil {
    private static IMUtil mInstance;

    public static IMUtil getInstance() {
        if (mInstance == null) {
            mInstance = new IMUtil();
        }
        return mInstance;
    }

    /**
     * 登陆
     */
    public void login(String user, String pwd, EMCallBack emCallBack) {
        //注册失败会抛出HyphenateException
        EMClient.getInstance().login(user, pwd, emCallBack);//同步方法
    }

    /**
     * 退出登陆
     */
    public void logout(EMCallBack emCallBack) {
        EMClient.getInstance().logout(true, emCallBack);
    }

    /**
     * 注册状态监听
     */
    public void setMyConnectionListener(EMConnectionListener connectionListener) {
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    public void sendText(Context context, String msgContent, FriendInfo receiver, String language){
        EMMessage emMessage = EMMessage.createTxtSendMessage(msgContent, receiver.getUsername());
        emMessage.setAttribute("language", "ch");
        EMClient.getInstance().chatManager().sendMessage(emMessage);
        msgSendSaveDB(context, receiver, UserDao.user, msgContent, language);
    }

    public void sendVoice(Context context, String filePath, FriendInfo receiver, int duration, String language) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, duration, receiver.getUsername());
        // 增加自己特定的属性
//        message.setAttribute("language", "ch");
        EMClient.getInstance().chatManager().sendMessage(message);
        msgSendSaveDB(context, receiver, UserDao.user, filePath, language);
    }

    public void init() {
        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        LogUtil.d("fengjian", "登陆");
    }

    /**
     * 消息监听
     */
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            for (EMMessage emMessage : messages) {
                LogUtil.d("fengjian", "收到消息" + emMessage.toString() + "  " + emMessage.getStringAttribute("language", ""));
            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.d("fengjian", "收到透传消息" + messages.toString());
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LogUtil.d("fengjian", "收到已读回执" + messages.toString());
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            LogUtil.d("fengjian", "收到已送达回执" + message.toString());
        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LogUtil.d("fengjian", "消息状态变动" + message.toString());
        }
    };

    private void msgSendSaveDB(Context context, FriendInfo receiver, LoginUser sender,
                               String tapeRecordFilePath, String language) {
        ChatMsg chatMsg = new ChatMsg(ChatMsg.TYPE_CONTENT_TAPE_RECORD, "", sender.getUsername(),
                sender.getNickName(), receiver.getUsername(), receiver.getNickname(), System.currentTimeMillis(),
                true, UserDao.user.getUsername(), language);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSavePath(tapeRecordFilePath);
        chatMsg.setResource(fileInfo);
        MessageDao.addMessage(context, chatMsg);
        EventManager.post(new EventMsg(EventMsg.SOCKET_ON_MESSAGE, chatMsg));
    }

    private void msgReceiveSaveDB(Context context, FriendInfo sender, String tapeRecordFilePath,
                                  String language) {
        ChatMsg chatMsg = new ChatMsg(ChatMsg.TYPE_CONTENT_TAPE_RECORD, "", sender.getUsername(),
                sender.getNickname(), sender.getUsername(), sender.getNickname(), System.currentTimeMillis(),
                false, UserDao.user.getUsername(), language);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSavePath(tapeRecordFilePath);
        chatMsg.setResource(fileInfo);
        MessageDao.addMessage(context, chatMsg);
        EventManager.post(new EventMsg(EventMsg.SOCKET_ON_MESSAGE, chatMsg));
    }

}
