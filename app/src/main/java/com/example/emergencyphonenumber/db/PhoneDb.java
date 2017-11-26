package com.example.emergencyphonenumber.db;

/**
 * Created by Promlert on 2017-11-26.
 */

public class PhoneDb {

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


}
