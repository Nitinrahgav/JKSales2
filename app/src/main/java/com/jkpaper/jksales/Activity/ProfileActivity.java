package com.jkpaper.jksales.Activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;

public class ProfileActivity extends AppCompatActivity {
    EditText firstName, lastName, mobile, email;
    SharedPreferences sharedPreferences;
    CircleImageView userImage;
    Uri uri;
    String filePath;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //mStorageRef = FirebaseStorage.getInstance().getReference();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        initViews();
    }

    private void initViews() {
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
                            /*Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                            StorageReference riversRef = mStorageRef.child("images/"+uri.getLastPathSegment());
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
        edtText.setFocusable(false);
    }
}
