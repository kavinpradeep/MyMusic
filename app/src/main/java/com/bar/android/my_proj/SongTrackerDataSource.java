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

public class SongTrackerDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SongTrackerHelper dbHelper;
    private String[] allColumns = { SongTrackerHelper.COLUMN_ID,SongTrackerHelper.COLUMN_SongName,SongTrackerHelper.COLUMN_SongLoc,
            SongTrackerHelper.COLUMN_UserGroup,SongTrackerHelper.COLUMN_Count,SongTrackerHelper.COLUMN_Duration,
            SongTrackerHelper.COLUMN_Artist,SongTrackerHelper.COLUMN_Year};

    public SongTrackerDataSource(Context context) {
        dbHelper = new SongTrackerHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SongTracker createSongEntry(int Count,String usergrp,String location,String songname, int Duration,String artist,int year) {
        ContentValues values = new ContentValues();
        values.put(SongTrackerHelper.COLUMN_SongName, songname);
        values.put(SongTrackerHelper.COLUMN_SongLoc, location);
        values.put(SongTrackerHelper.COLUMN_UserGroup, usergrp);
        values.put(SongTrackerHelper.COLUMN_Count, Count);
        values.put(SongTrackerHelper.COLUMN_Duration, Duration);
        values.put(SongTrackerHelper.COLUMN_Year, year);
        values.put(SongTrackerHelper.COLUMN_Artist, artist);
        Log.v("Before insertion", usergrp);
        long songid = database.insert(SongTrackerHelper.TABLE_SongTrack, null,
                values);
        Log.v("After insertion", songid + "");
        Cursor cursor = database.query(SongTrackerHelper.TABLE_SongTrack,
                allColumns, SongTrackerHelper.COLUMN_ID + " = " + songid, null,
                null, null, null);
        cursor.moveToFirst();
        Log.v("Artist:"+cursor.getString(6),"Year"+cursor.getInt(7)+"Count"+cursor.getInt(4));//+"SongCount"+newsong.getDuration())

        SongTracker newsong = null;
        //newsong = cursorToSong(cursor);
        cursor.close();
        return newsong;
    }

    public Boolean CheckSong(String location, String usergrp) {

        Cursor cursor = database.query(SongTrackerHelper.TABLE_SongTrack,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SongTracker song = cursorToSong(cursor);
            if(song.getSongLoc().equals(location) && song.getUserGroup().equals(usergrp))
                return true;
            else
                cursor.moveToNext();
        }
        cursor.close();
        return false;
    }

    public SongTracker updateCount(String usergrp) {

        return null;
    }

    public void updateDuration(String usergrp,int Count,int Duration, String location) {

        SongTracker song = null;
        Cursor cursor = database.query(SongTrackerHelper.TABLE_SongTrack,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            song = cursorToSong(cursor);
            if(song.getSongLoc().equals(location) && song.getUserGroup().equals(usergrp))
                break;
            else
                cursor.moveToNext();
        }


        ContentValues values = new ContentValues();
        values.put(SongTrackerHelper.COLUMN_SongName, song.getSongName());
        values.put(SongTrackerHelper.COLUMN_SongLoc, song.getSongLoc());
        values.put(SongTrackerHelper.COLUMN_UserGroup, song.getUserGroup());
        values.put(SongTrackerHelper.COLUMN_Count,Count);
        values.put(SongTrackerHelper.COLUMN_Duration, Duration);
        values.put(SongTrackerHelper.COLUMN_Year, song.getYear());
        values.put(SongTrackerHelper.COLUMN_Artist, song.getArtist());

        database.update(SongTrackerHelper.TABLE_SongTrack,values,"_id=" + song.getSongId(),null);
        //Log.v("Update : ","Location :"+song.getSongLoc()+"Artist : "+song.getArtist()+"Count"+song.getCount());
    }

    public List<SongTracker> getAllSongs() {
        List<SongTracker> allsongs = new ArrayList<SongTracker>();

        Cursor cursor = database.query(SongTrackerHelper.TABLE_SongTrack,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SongTracker song = cursorToSong(cursor);
            allsongs.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return allsongs;
    }


    private SongTracker cursorToSong(Cursor cursor) {
        SongTracker newsong = new SongTracker();
        newsong.setSongId(cursor.getLong(0));
        newsong.setSongName(cursor.getString(1));
        newsong.setSongLoc(cursor.getString(2));
        newsong.setUserGroup(cursor.getString(3));
        newsong.setCount(cursor.getInt(4));
        newsong.setDuration(cursor.getInt(5));
        newsong.setArtist(cursor.getString(6));
        newsong.setYear(cursor.getInt(7));
        Log.v("Year:"+newsong.getYear(),"Location"+newsong.getSongLoc()+"SongCount"+newsong.getCount()+"SongDuration"+newsong.getDuration());
        return newsong;
    }
}