package com.translation.model.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.translation.androidlib.utils.TimeUtil;
import com.translation.androidlib.utils.TransUtil;
import com.translation.model.db.SQLiteHelper;
import com.translation.model.entity.ChatMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren on 2018/12/21.
 */
public class MessageDao {

    public static final String TABLE_NAME = "h_message";
    public static final String SELF_ID = "self_id";
    public static final String MSG_ID = "msg_id";
    public static final String AVATAR = "avatar";
    public static final String CONTENT = "content";
    public static final String CHAT_ID = "chat_id";
    public static final String CHAT_NAME = "chat_name";
    public static final String CHAT_AVATAR = "chat_avatar";
    public static final String FROM_ID = "from_id";
    public static final String GROUP_ID = "group_id";
    public static final String MSG_TYPE = "msg_type";
    public static final String USERNAME = "username";
    public static final String FRIEND_NOTE = "friend_note";
    public static final String ORIGIN_PATH = "origin_path";
    public static final String DOWNLOAD_PATH = "download_path";
    public static final String SAVE_PATH = "save_path";
    public static final String FILE_NAME = "filename";
    public static final String FILE_TYPE = "file_type";
    public static final String FILE_LENGTH = "file_length";
    public static final String DURATION = "duration";
    public static final String TIMESTAMP = "timestamp";
    public static final String CREATE_TIME = "create_time";
    public static final String TOP_TIMESTAMP = "top_timestamp";
    public static final String TOP_TIME = "top_time";
    public static final String MINE = "mine";
    public static final String IS_VOICE = "is_voice";
    public static final String CHANNEL = "channel";
    public static final String CONNECT_STATE = "connect_state";
    public static final String SHOW = "show";
    public static final String LANGUAGE = "language";
    public static final String USER_ID = "user_id";
    private static final int PAGE_SIZE = 30;

    private static String insertSql = "insert into " + TABLE_NAME + "( " +
            MSG_ID + ", " +
            AVATAR + ", " +
            CONTENT + ", " +
            CHAT_ID + ", " +
            CHAT_NAME + ", " +
            CHAT_AVATAR + ", " +
            FROM_ID + ", " +
            GROUP_ID + ", " +
            MSG_TYPE + ", " +
            USERNAME + ", " +
            FRIEND_NOTE + ", " +
            ORIGIN_PATH + ", " +
            DOWNLOAD_PATH + ", " +
            SAVE_PATH + ", " +
            FILE_NAME + ", " +
            FILE_TYPE + ", " +
            FILE_LENGTH + ", " +
            DURATION + ", " +
            IS_VOICE + ", " +
            CHANNEL + ", " +
            CONNECT_STATE + ", " +
            TIMESTAMP + ", " +
            CREATE_TIME + ", " +
            TOP_TIMESTAMP + ", " +
            TOP_TIME + ", " +
            MINE + ", " +
            SHOW + ", " +
            LANGUAGE + ", " +
            USER_ID + " ) " +
            "select ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
            "where not exists (select " + MSG_ID + " from " + TABLE_NAME + " where " + MSG_ID + " = ?)";

    public static void addMessage(Context context, ChatMsg chatMsg) {
        List<ChatMsg> chatMsgList = new ArrayList<>();
        chatMsgList.add(chatMsg);
        addMessageList(context, chatMsgList);
    }

    public static boolean addMessageList(Context context, List<ChatMsg> chatMsgList) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ChatMsg chatMsg : chatMsgList) {
                String originPath = "";
                String downloadPath = "";
                String savePath = "";
                String fileName = "";
                int fileType = 0;
                long fileLength = 0;
                long duration = 0;
                int isVoice = 0;
                String channel = "";
                int connectState = 0;
                String createTime = "";
                String topTime = "";
                String matchMsgId = chatMsg.getMsgId();
                int showType = 1;
                if (chatMsg.getTimestamp() > 0) {
                    createTime = TimeUtil.timestampToStr(chatMsg.getTimestamp());
                }
                if (chatMsg.getTopTimestamp() > 0) {
                    topTime = TimeUtil.timestampToStr(chatMsg.getTopTimestamp());
                }
                db.execSQL(insertSql, new String[]{chatMsg.getMsgId(), chatMsg.getAvatar(), chatMsg.getContent(),
                        chatMsg.getChatId(), chatMsg.getChatName(), chatMsg.getChatAvatar(), chatMsg.getFromid(),
                        chatMsg.getGroupId(), chatMsg.getMsgType(), chatMsg.getUsername(), chatMsg.getFriendNote(),
                        originPath, downloadPath, savePath, fileName, String.valueOf(fileType), String.valueOf(fileLength),
                        String.valueOf(duration), String.valueOf(isVoice), channel, String.valueOf(connectState),
                        String.valueOf(chatMsg.getTimestamp()), createTime, String.valueOf(chatMsg.getTopTimestamp()),
                        topTime, String.valueOf(TransUtil.booleanToInt(chatMsg.isMine())), String.valueOf(showType),
                        chatMsg.getLanguage(), UserDao.user.getUserId(), matchMsgId});
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public static void clear(Context context) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where " + USER_ID + " = ?",
                new String[]{UserDao.user.getUserId()});
        db.close();
    }

    public static void clearShow(Context context) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL("update " + TABLE_NAME + " set " + SHOW + " = 0 where " + USER_ID + " = ?",
                new String[]{UserDao.user.getUserId()});
        db.close();
    }

    public static void updateShowById(Context context, String chatId, String msgType) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update " + TABLE_NAME + " set " + SHOW + " = 0 where " + CHAT_ID + " =? and " +
                            MSG_TYPE + " = ?",
                    new String[]{chatId, msgType});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void updateShowList(Context context, List<ChatMsg> msgList) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ChatMsg msg : msgList) {
                db.execSQL("update " + TABLE_NAME + " set " + SHOW + " = 0 where " + CHAT_ID + " =? and " +
                        MSG_TYPE + " = ?", new String[]{msg.getChatId(), msg.getMsgType()});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteMessage(Context context, ChatMsg chatMsg) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where " + SELF_ID + " = ? ",
                new String[]{chatMsg.getSelfId()});
        db.close();
    }

    public static void deleteMessageById(Context context, String chatId, String msgType) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("delete from " + TABLE_NAME + " where " + CHAT_ID + " = ? and " + MSG_TYPE + " = ?",
                    new String[]{chatId, msgType});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteMessageList(Context context, List<ChatMsg> msgList) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ChatMsg msg : msgList) {
                db.execSQL("delete from " + TABLE_NAME + " where " + CHAT_ID + " = ? and " + MSG_TYPE
                        + " = ?", new String[]{msg.getChatId(), msg.getMsgType()});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static int getCount(Context context, String chatId, String type) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where "
                        + CHAT_ID + " = ? and " + USER_ID + " = ? and " + MSG_TYPE + " =? and " + SHOW + " = 1",
                new String[]{chatId, UserDao.user.getUserId(), type});
        if (cursor != null) {
            int count = cursor.getCount();
            cursor.close();
            db.close();
            return count;
        } else {
            db.close();
            return 0;
        }
    }

    //打开聊天页，获取当前时间戳之前的消息列表
    public static List<ChatMsg> getMessageBeforeList(Context context, String chatId, String msgType,
                                                     long startTimestamp) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        if (UserDao.user != null) {
            String sql = "select * from ( select * from " + TABLE_NAME + " where " + USER_ID + " = ? and " +
                    CHAT_ID + " = ? and " + SHOW + " = 1 and " + MSG_TYPE + " = ? and " +
                    TIMESTAMP + " < ? order by " + TIMESTAMP + " desc limit ? ) order by " + TIMESTAMP;
            Cursor cursor = db.rawQuery(sql, new String[]{UserDao.user.getUserId(), chatId, msgType,
                    startTimestamp + "", PAGE_SIZE + ""});
            return parseCursorData(db, cursor);
        }
        return null;
    }

    //搜索聊天记录，获取时间戳之后的消息列表
    public static List<ChatMsg> getMessageAfterList(Context context, String chatId, String msgType,
                                                    long startTimestamp) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        if (UserDao.user != null) {
            String sql = "select * from " + TABLE_NAME + " where " + USER_ID + " = ? and " + CHAT_ID +
                    " = ? and " + SHOW + " = 1 and " + MSG_TYPE + " = ? and " + TIMESTAMP +
                    " >= ? order by " + TIMESTAMP;
            Cursor cursor = db.rawQuery(sql, new String[]{UserDao.user.getUserId(), chatId, msgType,
                    startTimestamp + ""});
            return parseCursorData(db, cursor);
        }
        return null;
    }

    public static List<ChatMsg> getMessageListBySearch(Context context, String searchStr) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        if (UserDao.user != null) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + USER_ID + " = ? and " +
                            SHOW + " = 1 and " + CONTENT + " like ? order by " + CHAT_ID + ", " +
                            TIMESTAMP + " desc",
                    new String[]{UserDao.user.getUserId(), "%" + searchStr + "%"});
            return parseCursorData(db, cursor);
        }
        return null;
    }

    public static ChatMsg getMessageByTimestamp(Context context, long createTimestamp) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        if (UserDao.user != null) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + USER_ID +
                            " = ? and " + SHOW + " = 1 and " + TIMESTAMP + " = ?",
                    new String[]{UserDao.user.getUserId(), createTimestamp + ""});
            List<ChatMsg> msgList = parseCursorData(db, cursor);
            if (msgList != null && !msgList.isEmpty()) {
                return msgList.get(0);
            }
        }
        return null;
    }

    private static List<ChatMsg> parseCursorData(SQLiteDatabase db, Cursor cursor) {
        List<ChatMsg> dataList = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ChatMsg chatMsg = new ChatMsg();
                chatMsg.setSelfId(cursor.getString(cursor.getColumnIndex(SELF_ID)));
                chatMsg.setMsgId(cursor.getString(cursor.getColumnIndex(MSG_ID)));
                chatMsg.setAvatar(cursor.getString(cursor.getColumnIndex(AVATAR)));
                chatMsg.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
                chatMsg.setChatId(cursor.getString(cursor.getColumnIndex(CHAT_ID)));
                chatMsg.setChatName(cursor.getString(cursor.getColumnIndex(CHAT_NAME)));
                chatMsg.setChatAvatar(cursor.getString(cursor.getColumnIndex(CHAT_AVATAR)));
                chatMsg.setFromid(cursor.getString(cursor.getColumnIndex(FROM_ID)));
                chatMsg.setGroupId(cursor.getString(cursor.getColumnIndex(GROUP_ID)));
                chatMsg.setMsgType(cursor.getString(cursor.getColumnIndex(MSG_TYPE)));
                chatMsg.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
                chatMsg.setFriendNote(cursor.getString(cursor.getColumnIndex(FRIEND_NOTE)));
                chatMsg.setTimestamp(cursor.getLong(cursor.getColumnIndex(TIMESTAMP)));
                chatMsg.setCreateTime(cursor.getString(cursor.getColumnIndex(CREATE_TIME)));
                chatMsg.setTopTimestamp(cursor.getLong(cursor.getColumnIndex(TOP_TIMESTAMP)));
                chatMsg.setTopTime(cursor.getString(cursor.getColumnIndex(TOP_TIME)));
                int mineStr = cursor.getInt(cursor.getColumnIndex(MINE));
                chatMsg.setMine(TransUtil.intToBoolean(mineStr));
                chatMsg.setShow(cursor.getInt(cursor.getColumnIndex(SHOW)));
                chatMsg.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
                chatMsg.setUserId(cursor.getString(cursor.getColumnIndex(USER_ID)));
                dataList.add(chatMsg);
            }
            cursor.close();
        }
        db.close();
        return dataList;
    }


}
