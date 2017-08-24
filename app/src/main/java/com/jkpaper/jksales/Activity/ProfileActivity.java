package com.jkpaper.jksales.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jkpaper.jksales.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    EditText firstName, lastName, mobile, email;
    SharedPreferences sharedPreferences;
    CircleImageView userImage;
    Uri uri;
    String filePath;
    ProgressDialog mProgress;
    private StorageReference storageRef;
    FirebaseStorage storage;
    Button btnUpdateProfile;
    TextView tvChangePassword, tvChangePin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //mStorageRef = FirebaseStorage.getInstance().getReference();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //storageRef = FirebaseStorage.getInstance().getReference();
        initViews();

    }

    private void initViews() {
        btnUpdateProfile = (Button)findViewById(R.id.btn_edit_profile);
        btnUpdateProfile.setOnClickListener(this);
        tvChangePassword = (TextView)findViewById(R.id.change_password);
        tvChangePin = (TextView)findViewById(R.id.change_pin);
        tvChangePassword.setOnClickListener(this);
        tvChangePin.setOnClickListener(this);
        userImage = (CircleImageView) findViewById(R.id.user_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TedPermission(getApplicationContext())
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not able to continue..\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .check();
            }
        });
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
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(ProfileActivity.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(final Uri uri) {
                            Log.d("file_path",uri.getPath());
                            /*StorageReference riversRef = storageRef.child("images/"+uri.getLastPathSegment());
                            UploadTask uploadTask = riversRef.putFile(uri);

// Register observers to listen for when the download is done or if it fails
                            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                }
                            });
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    Log.v("Download URI", String.valueOf(downloadUrl));
                                    userImage.setImageURI(uri);
                                }
                            });*/

                        }
                    })
                    .create();

            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }


    };
    private void disableInput(EditText edtText) {
        edtText.setEnabled(false);
    }
    int btnStatus = 0;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_edit_profile:
                if(btnStatus == 0){
                    firstName.setEnabled(true);
                    lastName.setEnabled(true);
                    mobile.setEnabled(true);
                    email.setEnabled(true);
                    firstName.setFocusable(true);
                    lastName.setFocusable(true);
                    mobile.setFocusable(true);
                    email.setFocusable(true);
                    btnUpdateProfile.setText("Save");
                    btnStatus = 1;
                }else{
                    mProgress = new ProgressDialog(ProfileActivity.this);
                    mProgress.setTitle("Updating Profile");
                    mProgress.setMessage("Please wait, while we update your personal information..");
                    updateProfileInfo();
                    mProgress.show();

                }
                break;
            case R.id.change_password:
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
                break;
            case R.id.change_pin:
                AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
                alertDialog.setTitle("Change mPin");
                alertDialog.setMessage("You need to login again to change your mPin.Do you want to continue??");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "LogOut",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                alertDialog.show();
                break;



        }
    }
    private void updateProfileInfo() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user", sharedPreferences.getString("user",""))
                .addFormDataPart("id", sharedPreferences.getString("user_id",""))
                .addFormDataPart("first",firstName.getText().toString())
                .addFormDataPart("last",lastName.getText().toString())
                .addFormDataPart("email",email.getText().toString())
                .addFormDataPart("mobile", String.valueOf(mobile.getText().toString()))
                .build();
        Request request = new Request.Builder().url(getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"update_profile.php").addHeader("Token","d75542712c868c1690110db641ba01a").addHeader("Data-For","1").post(requestBody).build();
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
                mProgress.dismiss();
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
                                    disableInput(firstName);
                                        disableInput(lastName);
                                        disableInput(email);
                                        disableInput(mobile);
                                        btnStatus = 0;
                                        btnUpdateProfile.setText("Edit");
                                        Log.d("resp","Success");
                                        sharedPreferences.edit().putString("user_name", firstName.getText().toString() + " " + lastName.getText().toString()).apply();
                                        sharedPreferences.edit().putString("user_email",email.getText().toString()).apply();
                                        sharedPreferences.edit().putString("user_first",firstName.getText().toString()).apply();
                                        sharedPreferences.edit().putString("user_last",lastName.getText().toString()).apply();
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
}
