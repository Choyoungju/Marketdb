package com.example.cho.marketdb;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DBInsertActivity extends ActionBarActivity {

    public static final String AUTHORITY = "com.example.cho.marketdb";
    public static final String BASE_PATH = "/DB_STORE";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + BASE_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbinsert);

        updateStoreDatabase();

        Button insertButton = (Button) findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText storeEditText = (EditText) findViewById(R.id.storeEditText);
                EditText categoryEditText = (EditText) findViewById(R.id.categoryEditText);
                EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);

                ContentValues cv = new ContentValues();
                cv.put(StoreDatabaseHelper.KEY_NAME, storeEditText.getText().toString());
                cv.put(StoreDatabaseHelper.KEY_CATEGORY, categoryEditText.getText().toString());
                cv.put(StoreDatabaseHelper.KEY_PHONE, phoneEditText.getText().toString());
                getContentResolver().insert(CONTENT_URI, cv);

                updateStoreDatabase();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            Intent intent = new Intent(DBInsertActivity.this, DBDeleteActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_update) {
            Intent intent = new Intent(DBInsertActivity.this, DBUpdateActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    public void updateStoreDatabase() {
        TextView textView = (TextView) findViewById(R.id.storeDBDisplay);
        String[] projection = {StoreDatabaseHelper.KEY_ID,
                StoreDatabaseHelper.KEY_NAME,
                StoreDatabaseHelper.KEY_CATEGORY,
                StoreDatabaseHelper.KEY_PHONE};

        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            textView.setText("");
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_CATEGORY));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_ID));
                String storeInfo = id + " : " + name + " - " + category + " - " + phone;

                textView.append("\n " + storeInfo);
            }
            cursor.close();
        }
    }
}