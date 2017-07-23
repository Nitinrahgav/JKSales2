package com.jkpaper.jksales.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akhgupta.easylocation.EasyLocationAppCompatActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jkpaper.jksales.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends EasyLocationAppCompatActivity {

    private static String LOGIN_URL = "http://www.nitinraghav.com/jkapi/login.php";

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView, edtOtp;
    private View mProgressView;
    private View mLoginFormView;
    String user_id, imeiNumber = "null", latitude = "null", longitude = "null";
    private LinearLayout otpLayout, loginFormLayout;
    TextView loginFaiedText;
    EasyLocationRequest easyLocationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not able to continue..\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mEmailView.setNextFocusDownId(R.id.password);
        edtOtp = (EditText)findViewById(R.id.edt_otp);
        otpLayout = (LinearLayout)findViewById(R.id.linear_layout_otp);
        loginFormLayout = (LinearLayout)findViewById(R.id.email_login_form);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setNextFocusDownId(R.id.email_sign_in_button);
        loginFaiedText = (TextView)findViewById(R.id.login_failed_text);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private String getImeiNumber() {
        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Log.v("IMEI",mngr.getDeviceId());
        return mngr.getDeviceId();
    }
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                    .setInterval(5000)
                    .setFastestInterval(5000);
            easyLocationRequest = new EasyLocationRequestBuilder()
                    .setLocationRequest(locationRequest)
                    .setFallBackToLastLocationTime(3000)
                    .build();
            requestSingleLocationFix(easyLocationRequest);
            imeiNumber = getImeiNumber();
            Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            latitude = "null";
            longitude = "null";
            imeiNumber = "null";
            Toast.makeText(LoginActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if(email.length() > 0){
                if(password.length() > 0){
                    if(checkInternetConnection()){
                        showProgress(true);
                        mAuthTask = new UserLoginTask(email, password);
                        mAuthTask.execute((Void) null);
                    }else{
                        showNotConnectedDialog();
                    }
                    
                }else{
                    loginFaiedText.setText("Please enter password.");
                    loginFaiedText.setVisibility(View.VISIBLE);
                }
            }else{
                loginFaiedText.setText("Please enter username.");
                loginFaiedText.setVisibility(View.VISIBLE);
            }

        }
    }

    private void showNotConnectedDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("Not Connected");
        alertDialog.setMessage("Please connect to Internet.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Retry",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        attemptLogin();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }






    @Override
    public void onLocationPermissionGranted() {

    }

    @Override
    public void onLocationPermissionDenied() {

    }

    @Override
    public void onLocationReceived(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onLocationProviderEnabled() {

    }

    @Override
    public void onLocationProviderDisabled() {

    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loginFaiedText.setVisibility(View.GONE);
                    showProgress(true);
                }
            });

            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("user", mEmail)
                        .addFormDataPart("pass", mPassword)
                        .addFormDataPart("imei",imeiNumber)
                        .addFormDataPart("lat",latitude)
                        .addFormDataPart("lang",longitude)
                        .build();
                Request request = new Request.Builder().url(LOGIN_URL).addHeader("Token","d75542712c868c1690110db641ba01a").post(requestBody).build();
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
                                                showProgress(false);
                                                loginFormLayout.setVisibility(View.GONE);
                                                otpLayout.setVisibility(View.VISIBLE);
                                            }
                                        });
                                        JSONObject obj_user=obj_data.getJSONObject("user");
                                        user_id=obj_user.getString("user_id");
                                        initOtpViews();
                                    }else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                loginFaiedText.setText(msgFinal);
                                                loginFaiedText.setVisibility(View.VISIBLE);
                                            }
                                        });

                                    }



                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loginFaiedText.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            }
                            catch (JSONException e)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loginFaiedText.setVisibility(View.VISIBLE);
                                    }
                                });

                                e.printStackTrace();
                            }
                            Log.v("Response", resp);

                                if (response.isSuccessful()) {
                            }else {
                                //
                            }
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loginFaiedText.setVisibility(View.VISIBLE);
                                }
                            });
                            System.out.println("Exception caught" + e.getMessage());
                        }
                    }

                });
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void initOtpViews() {

        edtOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 4){
                    Log.d("sequece",s.toString());
                    if(Objects.equals(s.toString(), "3489")){
                        Toast.makeText(getApplicationContext(),"You have successfully logged in",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivtyNav.class);
                        intent.putExtra("id",user_id);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putString("id",user_id).apply();
                        Log.d("user_id",user_id);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Entered OTP is not valid",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private boolean checkInternetConnection(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

