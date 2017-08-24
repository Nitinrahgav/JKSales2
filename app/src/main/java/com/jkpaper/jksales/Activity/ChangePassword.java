package com.jkpaper.jksales.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jkpaper.jksales.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener{
    EditText edtOldPassword, edtNewPassword, edtNewPassword2;
    Button btnSubmit;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edtOldPassword = (EditText)findViewById(R.id.edt_old_password);
        edtNewPassword = (EditText)findViewById(R.id.edt_new_password);
        edtNewPassword2 = (EditText)findViewById(R.id.edt_new_password2);
        btnSubmit = (Button)findViewById(R.id.btn_change_password);
        btnSubmit.setOnClickListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change_password:
                validateDetails();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void validateDetails() {
        if(!TextUtils.isEmpty(edtOldPassword.getText().toString())){
            if(!TextUtils.isEmpty(edtNewPassword.getText().toString())){
                if(!TextUtils.isEmpty(edtNewPassword2.getText().toString())){
                    if(Objects.equals(edtNewPassword.getText().toString(), edtNewPassword2.getText().toString())){
                        if (Objects.equals(edtOldPassword.getText().toString(), sharedPreferences.getString("password", ""))) {
                            requestPasswordChange();
                        }
                        else{
                            showToast("old password is wrong");
                        }
                    }else{
                        showToast("Password doesn't match");
                    }
                }else{
                    showToast("Enter the new password");
                }
            }else{
                showToast("Enter the new password");
            }
        }else{
            showToast("Enter the old password");
        }
    }

    private void requestPasswordChange() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user", sharedPreferences.getString("user",""))
                .addFormDataPart("id", sharedPreferences.getString("user_id",""))
                .addFormDataPart("pass",edtNewPassword2.getText().toString())
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"update_profile.php").addHeader("Token","d75542712c868c1690110db641ba01a").addHeader("Data-For","2").post(requestBody).build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {


            public static final String MODE_PRIVATE = "";

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Registration Error" + e.getMessage());
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                try {
                    String resp = response.body().string();
                    Log.d("Resp",resp);
                    try { JSONObject obj=new JSONObject(resp);

                        JSONObject obj_response=obj.getJSONObject("Response");
                        JSONObject obj_status=obj_response.getJSONObject("status");
                        JSONObject obj_data=obj_response.getJSONObject("data");
                        final String msgFinal = obj_data.getString("msg");
                        String statusFinal = obj_data.getString("status");
                        String type=obj_status.getString("type");
                        String message=obj_status.getString("message");
                        if(Objects.equals(type, "Success")){
                            if(Objects.equals(statusFinal,"1")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("resp","Success");
                                        sharedPreferences.edit().clear();
                                        showToast("Please login again to continue");
                                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                //JSONObject obj_user=obj_data.getJSONObject("user");

                            }else{

                            }



                        }else{

                        }
                    }
                    catch (JSONException e)
                    {


                        e.printStackTrace();
                    }
                    Log.v("Response", resp);

                    if (response.isSuccessful()) {
                    }else {
                        //
                    }
                } catch (IOException e) {

                    System.out.println("Exception caught" + e.getMessage());
                }
            }

        });
    }

    private void showToast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
}
