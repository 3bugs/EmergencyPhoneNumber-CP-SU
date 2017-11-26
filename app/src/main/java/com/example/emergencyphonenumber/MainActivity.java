package com.example.emergencyphonenumber;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.emergencyphonenumber.adapter.PhoneListAdapter;
import com.example.emergencyphonenumber.db.PhoneDbHelper;
import com.example.emergencyphonenumber.model.PhoneItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PhoneDbHelper mHelper;
    private SQLiteDatabase mDb;

    private ArrayList<PhoneItem> mPhoneItemList = new ArrayList<>();
    private PhoneListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new PhoneDbHelper(this);
        mDb = mHelper.getReadableDatabase();

        loadDataFromDb();

        mAdapter = new PhoneListAdapter(
                this,
                R.layout.item,
                mPhoneItemList
        );

        ListView lv = findViewById(R.id.list_view);
        lv.setAdapter(mAdapter);

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

        Button insertButon = findViewById(R.id.insert_button);
        insertButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phoneTitleEditText = findViewById(R.id.phone_title_edit_text);
                EditText phoneNumberEditText = findViewById(R.id.phone_number_edit_text);

                // todo: เพิ่มการตรวจสอบ input

                String phoneTitle = phoneTitleEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();

                ContentValues cv = new ContentValues();
                cv.put(PhoneDbHelper.COL_TITLE, phoneTitle);
                cv.put(PhoneDbHelper.COL_NUMBER, phoneNumber);
                cv.put(PhoneDbHelper.COL_PICTURE, "ic_launcher.png");

                mDb.insert(PhoneDbHelper.TABLE_NAME, null, cv);
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();

                mDb.delete(
                        PhoneDbHelper.TABLE_NAME,
                        "title=? AND number=?",
                        new String[]{"แจ้งเหตุด่วนเหตุร้าย", "199"}
                );
            }
        });

    } // ปิดเมธอด onCreate

    private void loadDataFromDb() {
        Cursor cursor = mDb.query(
                PhoneDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mPhoneItemList.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(PhoneDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(PhoneDbHelper.COL_TITLE));
            String number = cursor.getString(cursor.getColumnIndex(PhoneDbHelper.COL_NUMBER));
            String picture = cursor.getString(cursor.getColumnIndex(PhoneDbHelper.COL_PICTURE));

            PhoneItem item = new PhoneItem(id, title, number, picture);
            mPhoneItemList.add(item);
        }
    }
} // ปิดคลาส MainActivity
