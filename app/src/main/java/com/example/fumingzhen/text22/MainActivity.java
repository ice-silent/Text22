package com.example.fumingzhen.text22;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView userInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfoTextView = (TextView) findViewById(R.id.textview_userInfo);
        Button button = (Button) findViewById(R.id.button);
        String result = getContactInfo();
        userInfoTextView.setTextColor(Color.BLUE);
        userInfoTextView.setText("记录\t姓名\t电话\n" + result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=getContactInfo();
                userInfoTextView.setTextColor(Color.BLUE);
                userInfoTextView.setText("记录\t姓名\t电话\n"+result); }});
    }






    public String getContactInfo() {
        String result = "";
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        for (cursor.moveToFirst(); (!cursor.isAfterLast()); cursor.moveToNext()) {
            String contactId = cursor.getString(idIndex);
            result = result + contactId + "\t\t\t";
            result = result + cursor.getString(nameIndex) + "\t\t\t";
            Cursor phoneNumbers = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phoneNumbers.moveToNext()) {
                String strPhoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                result = result + strPhoneNumber + "\t\t\t";
            }
            phoneNumbers.close();
            Cursor emails = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
            while (emails.moveToNext()) {
                String strEmail = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                result = result + strEmail + "\t\t\t";
            }
            emails.close();
            result = result + "\n";
        }
        cursor.close();
        return result;
    }
}