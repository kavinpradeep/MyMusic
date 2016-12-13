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

public class NextSongDataSource {

    private SQLiteDatabase database;
    private NextSongTrackerHelper dbHelper;
    private String[] allColumns = { NextSongTrackerHelper.COLUMN_ID,NextSongTrackerHelper.COLUMN_SongId,NextSongTrackerHelper.COLUMN_UserGrp};

    public NextSongDataSource(Context context) {
        dbHelper = new NextSongTrackerHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public NextSongTracker getNextSong(String usergrp)
    {

        Cursor cursor = database.query(NextSongTrackerHelper.TABLE_NEXT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NextSongTracker nextsong = cursorToSong(cursor);
            if(nextsong.getUserGrp().equals(usergrp)) {
                Log.v("Next User :"+nextsong.getUserGrp(),"Next Song"+nextsong.getSongId());
                return nextsong;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return null;
    }

    public void deleteNextSong(NextSongTracker nextplay)
    {
        Cursor cursor = database.query(NextSongTrackerHelper.TABLE_NEXT,
                allColumns, null, null, null, null, null);
        database.delete(NextSongTrackerHelper.TABLE_NEXT, NextSongTrackerHelper.COLUMN_SongId
                + " = \"" + nextplay.getSongId() + "\" AND " + NextSongTrackerHelper.COLUMN_UserGrp + " = \"" + nextplay.getUserGrp()+"\"", null);
        cursor.close();
    }

    public NextSongTracker createSongEntry(String location,String usergrp) {
        ContentValues values = new ContentValues();
        values.put(NextSongTrackerHelper.COLUMN_UserGrp, usergrp);
        values.put(NextSongTrackerHelper.COLUMN_SongId, location);
        long songid = database.insert(NextSongTrackerHelper.TABLE_NEXT, null,
                values);
        Log.v("After insertion", songid + "");
        Cursor cursor = database.query(NextSongTrackerHelper.TABLE_NEXT,
                allColumns, NextSongTrackerHelper.COLUMN_ID + " = " + songid, null,
                null, null, null);
        cursor.moveToFirst();
        Log.v("Location:", "" + cursor.getString(1) + "User :" + cursor.getString(2));
        cursor.close();
        return null;
    }

    public List<NextSongTracker> getAllNexts() {
        List<NextSongTracker> nexts = new ArrayList<NextSongTracker>();

        Cursor cursor = database.query(NextSongTrackerHelper.TABLE_NEXT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NextSongTracker next = cursorToSong(cursor);
            nexts.add(next);
            cursor.moveToNext();
        }
        cursor.close();
        return nexts;
    }

    private NextSongTracker cursorToSong(Cursor cursor) {
        NextSongTracker newsong = new NextSongTracker();
        newsong.setId(cursor.getInt(0));
        newsong.setSongId(cursor.getString(1));
        newsong.setUserGrp(cursor.getString(2));
        Log.v("ID : " + cursor.getInt(0), " Song ID : " + cursor.getString(1) + "User" + cursor.getString(2));
        return newsong;
    }
}