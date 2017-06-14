package com.example.cho.marketdb;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cho on 2015-05-06.
 */
public class DBUpdateActivity extends ActionBarActivity{

    public static final String AUTHORITY = "com.example.cho.marketdb";
    public static final String BASE_PATH = "/DB_STORE";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + BASE_PATH);

    EditText idEditText;
    EditText nameEditText;
    EditText categoryEditText;
    EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbupdate);


        updateStoreDatabase();

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        categoryEditText = (EditText) findViewById(R.id.categoryEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        idEditText = (EditText) findViewById(R.id.idEditText);


        Button updateButton = (Button) findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(StoreDatabaseHelper.KEY_CATEGORY, categoryEditText.getText().toString());
                cv.put(StoreDatabaseHelper.KEY_PHONE, phoneEditText.getText().toString());
                cv.put(StoreDatabaseHelper.KEY_NAME, nameEditText.getText().toString());
               getContentResolver().update(CONTENT_URI,cv,StoreDatabaseHelper.KEY_ID + " = ? ",
                       new String[] { idEditText.getText().toString()});

                updateStoreDatabase();
            }
        });


        Button getbutton = (Button) findViewById(R.id.getButton);


        getbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String[] projection = { StoreDatabaseHelper.KEY_ID,
                        StoreDatabaseHelper.KEY_ID,
                        StoreDatabaseHelper.KEY_NAME,
                        StoreDatabaseHelper.KEY_CATEGORY,
                        StoreDatabaseHelper.KEY_PHONE};
                Cursor cursor = getContentResolver().query(CONTENT_URI, projection, StoreDatabaseHelper.KEY_ID + " = ? ",
                        new String[] { idEditText.getText().toString() }, null);

                if (cursor!=null){
                    cursor.moveToFirst();


                    String name = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_NAME));
                    String category = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_CATEGORY));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_PHONE));

                nameEditText.setText(name);
                    categoryEditText.setText(category);
                    phoneEditText.setText(phone);

                    cursor.close();

                }
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

        if(id==R.id.action_delete){
            Intent intent  = new Intent(DBUpdateActivity.this, DBDeleteActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        else if(id == R.id.action_update){
            Intent intent = new Intent(DBUpdateActivity.this ,DBUpdateActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateStoreDatabase(){
        TextView textView = (TextView) findViewById(R.id.storeDBDisplay);
        String[] projection = {StoreDatabaseHelper.KEY_ID,
                StoreDatabaseHelper.KEY_NAME,
                StoreDatabaseHelper.KEY_CATEGORY,
                StoreDatabaseHelper.KEY_PHONE   };

        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);
        if (cursor != null){
            textView.setText("");
            while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_CATEGORY));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_PHONE));
                String storeInfo =id + " : " +name+" - " + category + " - " + phone;

                textView.append("\n " + storeInfo);
            }
            cursor.close();
        }


    }

}


