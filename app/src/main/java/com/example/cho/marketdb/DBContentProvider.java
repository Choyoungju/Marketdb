package com.example.cho.marketdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by cho on 2015-05-06.
 */
public class DBContentProvider extends ContentProvider {

    StoreDatabaseHelper storeDatabase;

    @Override
    public boolean onCreate() {

        storeDatabase = new StoreDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = storeDatabase.getReadableDatabase();
        Cursor cursor = db.query(StoreDatabaseHelper.TABLE_STORE, projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = storeDatabase.getWritableDatabase();
        db.insert(StoreDatabaseHelper.TABLE_STORE, null, values);
        db.close();
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = storeDatabase.getWritableDatabase();
        db.delete(StoreDatabaseHelper.TABLE_STORE, selection, selectionArgs);
        db.close();

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = storeDatabase.getWritableDatabase();
        db.update(StoreDatabaseHelper.TABLE_STORE, values, selection, selectionArgs);
        db.close();

        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}

