package com.translation.model.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.translation.model.db.SQLiteHelper;
import com.translation.model.entity.LoginUser;

/**
 * Created by Darren on 2017/7/11.
 */

public class UserDao {

    public static final String TABLE_NAME = "h_user";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String PHONE_NUM = "phone_num";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static LoginUser user;

    public static void setUser(Context context, LoginUser user){
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(USERNAME, user.getUsername());
        values.put(PHONE_NUM, user.getPhoneNum());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public static void deleteUser(Context context) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        db.close();
    }

    public static LoginUser getUser(Context context) {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            LoginUser loginUser = new LoginUser();
            loginUser.setUserId(cursor.getString(cursor.getColumnIndex(USER_ID)));
            loginUser.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
            loginUser.setPhoneNum(cursor.getString(cursor.getColumnIndex(PHONE_NUM)));
            loginUser.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            loginUser.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
            cursor.close();
            db.close();
            return loginUser;
        }
        db.close();
        return null;
    }



}
