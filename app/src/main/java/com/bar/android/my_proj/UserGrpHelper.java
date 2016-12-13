package com.bar.android.my_proj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kavin on 12/7/2016.
 */

public class UserGrpHelper extends SQLiteOpenHelper {

    public static final String TABLE_UserGrp = "UserGrp16";
    public static final String COLUMN_ID = "UserGrpID";
    public static final String COLUMN_UserGrp = "UserGrp";

    private static final String DATABASE_NAME = "music16.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_UserGrp + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_UserGrp
            + " text not null);";

    public UserGrpHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v("Helper", "Context");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        Log.v("UserGrp", "Creater");
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UserGrp);
        onCreate(db);
    }
}
