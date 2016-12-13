package com.bar.android.my_proj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kavin on 12/7/2016.
 */

public class PushTrackerHelper extends SQLiteOpenHelper {

    public static final String TABLE_Push = "PushSong";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SongId = "SongId";

    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_Push + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SongId
            + " text not null);";

    public PushTrackerHelper(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Push);
        onCreate(db);
    }
}
