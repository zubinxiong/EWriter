package com.zubin.ewriter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zubin on 15/6/3.
 */
public class WriterDB extends SQLiteOpenHelper {

    public static final String ID = "_id";
    public static final String CONTENT = "content";
    public static final String CREATEDTIME = "createdtime";
    public static final String STARTED = "started";

    public final static String EWRITER_TABLE_NAME = "Writer";
    public static final String NAME = "EWriter";
    public static final int VERSION = 1;



    private final String CREATE_WRITE_TABLE = "create table Writer("
            + ID + " integer primary key autoincrement,"
            + CONTENT + " text,"
            + STARTED + " integer,"
            + CREATEDTIME + " text)";

    public WriterDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public WriterDB(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WRITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getWritableDatabase() {

        return super.getWritableDatabase();
    }
}
