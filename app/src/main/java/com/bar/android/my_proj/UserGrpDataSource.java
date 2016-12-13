package com.bar.android.my_proj;

/**
 * Created by kavin on 12/7/2016.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserGrpDataSource {

    // Database fields
    private SQLiteDatabase database;
    private UserGrpHelper dbHelper;
    private String[] allColumns = { UserGrpHelper.COLUMN_ID,
            UserGrpHelper.COLUMN_UserGrp };

    public UserGrpDataSource(Context context) {
        dbHelper = new UserGrpHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserGrpTracker createUserGrp(String usergrp) {
        ContentValues values = new ContentValues();
        values.put(UserGrpHelper.COLUMN_UserGrp, usergrp);
        long insertId = database.insert(UserGrpHelper.TABLE_UserGrp, null,
                values);
        Cursor cursor = database.query(UserGrpHelper.TABLE_UserGrp,
                allColumns, UserGrpHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        UserGrpTracker newusr = cursorToUser(cursor);
        cursor.close();
        return newusr;
    }

    public void deleteUsr(){//(UserGrpTracker usr) {
        long id = 2;//usr.getUserGrpID();
        System.out.println("Comment deleted with id: " + id);
        database.delete(UserGrpHelper.TABLE_UserGrp, UserGrpHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<UserGrpTracker> getAllUsers() {
        List<UserGrpTracker> users = new ArrayList<UserGrpTracker>();

        Cursor cursor = database.query(UserGrpHelper.TABLE_UserGrp,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserGrpTracker user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }

    private UserGrpTracker cursorToUser(Cursor cursor) {
        UserGrpTracker usrgrp = new UserGrpTracker();
        usrgrp.setUserGrpID(cursor.getLong(0));
        usrgrp.setUserGroup(cursor.getString(1));
        Log.v("Name:"+cursor.getLong(0),cursor.getString(1));
        return usrgrp;
    }
}