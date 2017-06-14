package com.example.cho.marketdb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DBDeleteActivity extends ActionBarActivity {
    public static final String AUTHORITY = "com.example.cho.marketdb";
    public static final String BASE_PATH = "/DB_STORE";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + BASE_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dbdelete);
        updateStoreDatabase();
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"dd",Toast.LENGTH_SHORT).show();
                EditText idEditText = (EditText)findViewById(R.id.idEditText);
               // Toast.makeText(getApplicationContext(),idEditText.getText().toString(),Toast.LENGTH_SHORT).show();
                getContentResolver().delete(CONTENT_URI,StoreDatabaseHelper.KEY_ID + "=?", new String[]{idEditText.getText().toString()});
                updateStoreDatabase();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_insert){
            Intent intent = new Intent(DBDeleteActivity.this,DBInsertActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if (id==R.id.action_update)
        {
            Intent intent = new Intent(DBDeleteActivity.this,DBInsertActivity.class);
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
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(StoreDatabaseHelper.KEY_PHONE));
                String storeInfo = id + " : " + name + " -" + category + " - " + phone;

                textView.append(("\n" + storeInfo));

            }

            cursor.close();
        }
    }
}