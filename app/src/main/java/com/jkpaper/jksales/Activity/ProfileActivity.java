package com.jkpaper.jksales.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.jkpaper.jksales.R;

public class ProfileActivity extends AppCompatActivity {
    EditText firstName, lastName, mobile, email;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        initViews();
    }

    private void initViews() {
        firstName = (EditText)findViewById(R.id.edt_first_name);
        lastName = (EditText)findViewById(R.id.edt_last_name);
        mobile = (EditText)findViewById(R.id.edt_mobile);
        email = (EditText)findViewById(R.id.edt_email);
        firstName.setText(sharedPreferences.getString("user_first",""));
        lastName.setText(sharedPreferences.getString("user_last",""));
        mobile.setText(sharedPreferences.getString("user_mobile",""));
        email.setText(sharedPreferences.getString("user_email",""));
        disableInput(firstName);
        disableInput(lastName);
        disableInput(mobile);
        disableInput(email);
    }

    private void disableInput(EditText edtText) {
        edtText.setFocusable(false);
    }
}
