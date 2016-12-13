package com.bar.android.my_proj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kavin on 12/7/2016.
 */

public class SongTrackerHelper extends SQLiteOpenHelper {

    public static final String TABLE_SongTrack = "SongTrack12";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SongName = "SongName";
    public static final String COLUMN_SongLoc = "SongLoc";
    public static final String COLUMN_UserGroup = "UserGroup";
    public static final String COLUMN_Count = "Count";
    public static final String COLUMN_Duration = "Duration";
    public static final String COLUMN_Artist = "Artist";
    public static final String COLUMN_Year = "Year";


    private static final String DATABASE_NAME = "music13.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SongTrack + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_SongName + " text not null, "
            + COLUMN_SongLoc + " text not null, "
            + COLUMN_UserGroup + " text not null, "
            + COLUMN_Count + " integer not null,"   // the track id
            + COLUMN_Duration + " integer not null," //like or dislike
            + COLUMN_Artist + " text not null,"
            + COLUMN_Year + " integer not null);";

    public SongTrackerHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v("SongTracker","Created");
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SongTrack);
        onCreate(db);
    }
}
