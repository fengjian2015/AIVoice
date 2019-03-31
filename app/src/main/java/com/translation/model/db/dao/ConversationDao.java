package com.translation.model.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.translation.androidlib.utils.TimeUtil;
import com.translation.model.db.SQLiteHelper;
import com.translation.model.entity.ChatMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren on 2019/3/4
 */
public class ConversationDao {

    public static final String TABLE_NAME = "h_conversation";
    public static final String SELF_ID = "self_id";
    public static final String CHAT_ID = "chat_id";
    public static final String CHAT_NAME = "chat_name";
    public static final String CHAT_AVATAR = "chat_avatar";
    public static final String MSG_TYPE = "msg_type";
    public static final String FRIEND_NOTE = "friend_note";
    public static final String CONTENT = "content";
    public static final String TIMESTAMP = "timestamp";
    public static final String CREATE_TIME = "create_time";
    public static final String TOP_MARK = "top_mark";
    public static final String TOP_TIMESTAMP = "top_timestamp";
    public static final String TOP_TIME = "top_time";
    public static final String SHIELD_MARK = "shield_mark";
    public static final String CHAT_OPEN = "chat_open";
    public static final String UNREAD = "unread";
    public static final String OFF_MSG_NUM = "off_msg_num";
    public static final String USER_ID = "user_id";

    public static void addConversation(Context context, ChatMsg chatMsg) {
        List<ChatMsg> chatMsgList = new ArrayList<>();
        chatMsgList.add(chatMsg);
        addConversationList(context, chatMsgList);
    }

    public static void addConversationList(Context context, List<ChatMsg> chatMsgList) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ChatMsg chatMsg : chatMsgList) {
                ContentValues values = new ContentValues();
                values.put(CHAT_ID, chatMsg.getChatId());
                values.put(CHAT_NAME, chatMsg.getChatName());
                values.put(CHAT_AVATAR, chatMsg.getChatAvatar());
                values.put(MSG_TYPE, chatMsg.getMsgType());
                values.put(FRIEND_NOTE, chatMsg.getFriendNote());
                values.put(CONTENT, chatMsg.getContent());
                values.put(TIMESTAMP, chatMsg.getTimestamp());
                if (chatMsg.getTimestamp() > 0) {
                    values.put(CREATE_TIME, TimeUtil.timestampToStr(chatMsg.getTimestamp()));
                }
                values.put(TOP_MARK, chatMsg.getTopMark());
                values.put(TOP_TIMESTAMP, chatMsg.getTopTimestamp());
                if (chatMsg.getTopTimestamp() > 0) {
                    values.put(TOP_TIME, TimeUtil.timestampToStr(chatMsg.getTopTimestamp()));
                }
                values.put(SHIELD_MARK, chatMsg.getShieldMark());
                values.put(CHAT_OPEN, chatMsg.getChatOpen());
                if (chatMsg.getChatOpen() == 0) {
                    values.put(UNREAD, chatMsg.getUnread());
                    values.put(OFF_MSG_NUM, chatMsg.getOffMsgNum());
                }
                values.put(USER_ID, UserDao.user.getUserId());
                db.replace(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public static ChatMsg getConversation(Context context, String chatId, String msgType) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + CHAT_ID + " = ? and " +
                USER_ID + " = ? and " + MSG_TYPE + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{chatId, UserDao.user.getUserId(), msgType});
        ChatMsg chatMsg = null;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                chatMsg = packChatMsgData(cursor);
            }
            cursor.close();
        }
        db.close();
        return chatMsg;
    }

    public static List<ChatMsg> getConversationList(Context context) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        String sql = "select * from (select * from " + TABLE_NAME + " where " + USER_ID + " = ? " +
                "order by " + TIMESTAMP + ") order by " + TOP_TIMESTAMP + " desc";
        Cursor cursor = db.rawQuery(sql, new String[]{UserDao.user.getUserId()});
        List<ChatMsg> chatMsgList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                chatMsgList.add(packChatMsgData(cursor));
            }
            cursor.close();
        }
        db.close();
        return chatMsgList;
    }

    public static void clearConversationById(Context context, String chatId, String msgType) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update " + TABLE_NAME + " set " + CONTENT + " = ? where " + CHAT_ID +
                            " = ? and " + MSG_TYPE + " = ? and " + USER_ID + " =? ",
                    new String[]{"", chatId, msgType, UserDao.user.getUserId()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteConversation(Context context, String chatId, String msgType) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_NAME, CHAT_ID + " = ? and " + MSG_TYPE + " = ? and " + USER_ID + " = ?",
                    new String[]{chatId, msgType, UserDao.user.getUserId()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteConversation(Context context, ChatMsg chatMsg) {
        List<ChatMsg> chatMsgList = new ArrayList<>();
        chatMsgList.add(chatMsg);
        deleteConversationList(context, chatMsgList);
    }

    public static void deleteConversationList(Context context, List<ChatMsg> chatMsgList) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ChatMsg chatMsg : chatMsgList) {
                db.delete(TABLE_NAME, CHAT_ID + " = ? and " + MSG_TYPE + " = ? and " + USER_ID + " = ?",
                        new String[]{chatMsg.getChatId(), chatMsg.getMsgType(), UserDao.user.getUserId()});
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private static ChatMsg packChatMsgData(Cursor cursor) {
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
        chatMsg.setChatId(cursor.getString(cursor.getColumnIndex(CHAT_ID)));
        chatMsg.setChatName(cursor.getString(cursor.getColumnIndex(CHAT_NAME)));
        chatMsg.setChatAvatar(cursor.getString(cursor.getColumnIndex(CHAT_AVATAR)));
        chatMsg.setMsgType(cursor.getString(cursor.getColumnIndex(MSG_TYPE)));
        chatMsg.setFriendNote(cursor.getString(cursor.getColumnIndex(FRIEND_NOTE)));
        chatMsg.setTimestamp(cursor.getLong(cursor.getColumnIndex(TIMESTAMP)));
        chatMsg.setCreateTime(cursor.getString(cursor.getColumnIndex(CREATE_TIME)));
        chatMsg.setTopMark(cursor.getInt(cursor.getColumnIndex(TOP_MARK)));
        chatMsg.setTopTimestamp(cursor.getLong(cursor.getColumnIndex(TOP_TIMESTAMP)));
        chatMsg.setTopTime(cursor.getString(cursor.getColumnIndex(TOP_TIME)));
        chatMsg.setShieldMark(cursor.getInt(cursor.getColumnIndex(SHIELD_MARK)));
        chatMsg.setChatOpen(cursor.getInt(cursor.getColumnIndex(CHAT_OPEN)));
        chatMsg.setUnread(cursor.getInt(cursor.getColumnIndex(UNREAD)));
        chatMsg.setOffMsgNum(cursor.getInt(cursor.getColumnIndex(OFF_MSG_NUM)));
        chatMsg.setUserId(cursor.getString(cursor.getColumnIndex(USER_ID)));
        return chatMsg;
    }

}
