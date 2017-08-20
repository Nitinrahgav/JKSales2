package com.jkpaper.jksales.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jkpaper.jksales.R;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener{
    EditText edtOldPassword, edtNewPassword, edtNewPassword2;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edtOldPassword = (EditText)findViewById(R.id.edt_old_password);
        edtNewPassword = (EditText)findViewById(R.id.edt_new_password);
        edtNewPassword2 = (EditText)findViewById(R.id.edt_new_password2);
        btnSubmit = (Button)findViewById(R.id.btn_change_password);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change_password:
                break;
        }
    }
}
