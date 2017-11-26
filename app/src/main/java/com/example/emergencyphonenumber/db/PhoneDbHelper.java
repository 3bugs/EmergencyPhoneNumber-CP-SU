package com.example.emergencyphonenumber.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Promlert on 2017-11-26.
 */

public class PhoneDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "phone.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "phone_number";
    private static final String COL_ID = "_id";
    private static final String COL_TITLE = "title";
    private static final String COL_NUMBER = "number";
    private static final String COL_PICTURE = "picture";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE + " TEXT, "
            + COL_NUMBER + " TEXT, "
            + COL_PICTURE + " TEXT)";

    public PhoneDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, "แจ้งเหตุด่วนเหตุร้าย");
        cv.put(COL_NUMBER, "191");
        cv.put(COL_PICTURE, "number0001.jpg");
        db.insert(TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(COL_TITLE, "แจ้งเหตุเพลิงไหม้");
        cv.put(COL_NUMBER, "199");
        cv.put(COL_PICTURE, "number0002.jpg");
        db.insert(TABLE_NAME, null, cv);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
