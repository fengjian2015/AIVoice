package com.translation.androidlib.utils;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import java.util.List;
public class IMUtil {
    private static IMUtil mInstance;

    public static IMUtil getInstance(){
        if(mInstance == null){
            mInstance=new IMUtil();
        }
        return mInstance;
    }


    /**
     * 登陆
     *
     * @param user
     * @param pwd
     */
    public void login(String user, String pwd ,EMCallBack emCallBack) {
        //注册失败会抛出HyphenateException
        EMClient.getInstance().login(user, pwd, emCallBack);//同步方法
    }

    /**
     * 退出登陆
     */
    public void logout(EMCallBack emCallBack) {
        EMClient.getInstance().logout(true,emCallBack );
    }

    /**
     * 注册状态监听
     */
    public void setMyConnectionListener(EMConnectionListener connectionListener){
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    public void sendVoice(String filePath,int length,String toChatUsername){
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length,toChatUsername);
        // 增加自己特定的属性
        message.setAttribute("language", "ch");
        EMClient.getInstance().chatManager().sendMessage(message);
    }



    public void  init(){
        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        LogUtil.d("fengjian", "登陆" );
    }

    /**
     * 消息监听
     */
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            for (EMMessage emMessage:messages){
                LogUtil.d("fengjian", "收到消息" +emMessage.toString()+"  "+emMessage.getStringAttribute("language",""));
            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.d("fengjian", "收到透传消息" +messages.toString());
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LogUtil.d("fengjian", "收到已读回执" +messages.toString());
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            LogUtil.d("fengjian", "收到已送达回执" +message.toString());
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LogUtil.d("fengjian", "消息状态变动" +message.toString());
        }
    };

}
