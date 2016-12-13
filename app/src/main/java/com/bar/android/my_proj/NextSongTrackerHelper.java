package com.bar.android.my_proj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kavin on 12/7/2016.
 */

public class NextSongTrackerHelper extends SQLiteOpenHelper {

    public static final String TABLE_NEXT = "NextSong17";
    public static final String COLUMN_ID = "Now_id";
    public static final String COLUMN_SongId = "SongId"; //Loacation
    public static final String COLUMN_UserGrp = "UserGrp";

    private static final String DATABASE_NAME = "music17.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NEXT + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_UserGrp
            + " text, " + COLUMN_SongId
            + " text not null);";

    public NextSongTrackerHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEXT);
        onCreate(db);
    }
}
