package com.example.cho.marketdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cho on 2015-05-06.
 */
public class StoreDatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION =1;
    private  static final String DATABASE_NAME = "store.db";
    public static final String TABLE_STORE="stores";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PHONE= "phone";

    public StoreDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onCreate (SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE = " CREATE TABLE "+ TABLE_STORE + "( " +
                KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT,"
                + KEY_CATEGORY + " TEXT, "
                + KEY_PHONE  + " TEXT " + " ) ";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_STORE);
        onCreate(db);
    }

}
