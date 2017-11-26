package com.example.emergencyphonenumber;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.emergencyphonenumber.adapter.PhoneListAdapter;
import com.example.emergencyphonenumber.db.PhoneDbHelper;
import com.example.emergencyphonenumber.model.PhoneItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PhoneDbHelper mHelper;
    private SQLiteDatabase mDb;

    private ArrayList<PhoneItem> mPhoneItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new PhoneDbHelper(this);
        mDb = mHelper.getReadableDatabase();

        Cursor cursor = mDb.query(
                PhoneDbHelper.TABLE_NAME,
                null,
                "category=1",
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(PhoneDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(PhoneDbHelper.COL_TITLE));
            String number = cursor.getString(cursor.getColumnIndex(PhoneDbHelper.COL_NUMBER));
            String picture = cursor.getString(cursor.getColumnIndex(PhoneDbHelper.COL_PICTURE));

            PhoneItem item = new PhoneItem(id, title, number, picture);
            mPhoneItemList.add(item);
        }

        PhoneListAdapter adapter = new PhoneListAdapter(
                this,
                R.layout.item,
                mPhoneItemList
        );

        ListView lv = findViewById(R.id.list_view);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PhoneItem item = mPhoneItemList.get(position);
                String phoneNumber = item.number;

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
    }
}
