package com.translation.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.translation.model.db.dao.ConversationDao;
import com.translation.model.db.dao.MessageDao;
import com.translation.model.db.dao.UserDao;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AIVoice.db"; //数据库名称
    private static final int VERSION = 1; //版本

    private final String TABLE_USER = "create table "
            + UserDao.TABLE_NAME + "("
            + UserDao.USER_ID + " integer primary key autoincrement, "
            + UserDao.USERNAME + " varchar, "
            + UserDao.EMAIL + " varchar, "
            + UserDao.PHONE_NUM + " varchar);";

    private final String TABLE_MESSAGE = "create table "
            + MessageDao.TABLE_NAME + "("
            + MessageDao.SELF_ID + " integer primary key autoincrement, "
            + MessageDao.MSG_ID + " varchar, "
            + MessageDao.AVATAR + " varchar, "
            + MessageDao.CONTENT + " varchar, "
            + MessageDao.CHAT_ID + " varchar, "
            + MessageDao.CHAT_NAME + " varchar, "
            + MessageDao.CHAT_AVATAR + " varchar, "
            + MessageDao.FROM_ID + " varchar, "
            + MessageDao.GROUP_ID + " varchar, "
            + MessageDao.MSG_TYPE + " varchar, "
            + MessageDao.USERNAME + " varchar, "
            + MessageDao.FRIEND_NOTE + " varchar, "
            + MessageDao.ORIGIN_PATH + " varchar, "
            + MessageDao.DOWNLOAD_PATH + " varchar, "
            + MessageDao.SAVE_PATH + " varchar, "
            + MessageDao.FILE_NAME + " varchar, "
            + MessageDao.FILE_TYPE + " varchar, "
            + MessageDao.FILE_LENGTH + " varchar, "
            + MessageDao.DURATION + " varchar, "
            + MessageDao.TIMESTAMP + " float, "
            + MessageDao.CREATE_TIME + " varchar, "
            + MessageDao.TOP_TIMESTAMP + " float, "
            + MessageDao.TOP_TIME + " varchar, "
            + MessageDao.MINE + " varchar, "
            + MessageDao.IS_VOICE + " varchar, "
            + MessageDao.CHANNEL + " varchar, "
            + MessageDao.CONNECT_STATE + " varchar, "
            + MessageDao.SHOW + " varchar, "
            + MessageDao.USER_ID + " varchar);";

    private final String TABLE_CONVERSATION = "create table "
            + ConversationDao.TABLE_NAME + "("
            + ConversationDao.SELF_ID + " integer primary key autoincrement, "
            + ConversationDao.CONTENT + " varchar, "
            + ConversationDao.CHAT_ID + " varchar unique, "
            + ConversationDao.CHAT_NAME + " varchar, "
            + ConversationDao.CHAT_AVATAR + " varchar, "
            + ConversationDao.MSG_TYPE + " varchar, "
            + ConversationDao.FRIEND_NOTE + " varchar, "
            + ConversationDao.TIMESTAMP + " float, "
            + ConversationDao.CREATE_TIME + " varchar, "
            + ConversationDao.TOP_MARK + " varchar, "
            + ConversationDao.TOP_TIMESTAMP + " float, "
            + ConversationDao.TOP_TIME + " varchar, "
            + ConversationDao.SHIELD_MARK + " integer, "
            + ConversationDao.CHAT_OPEN + " integer, "
            + ConversationDao.UNREAD + " integer, "
            + ConversationDao.OFF_MSG_NUM + " integer, "
            + ConversationDao.USER_ID + " varchar);";

    public SQLiteHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER);
        db.execSQL(TABLE_MESSAGE);
        db.execSQL(TABLE_CONVERSATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
