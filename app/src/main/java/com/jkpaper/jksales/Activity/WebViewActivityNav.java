package com.jkpaper.jksales.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jkpaper.jksales.R;

public class WebViewActivityNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    WebView wv1;
    String url, finalUrl;
    ProgressBar mProgress;
    ProgressBar crpv;
    TextView tvProgress, tvLoading, tvUserName, tvUserEmail;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_nav);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(isNetworkConnected()){
            url = getIntent().getStringExtra("url_web_view");
            finalUrl = url + "&Token=d75542712c868c1690110db641ba01a";
            Log.d("url",finalUrl);
            if(getIntent().getStringExtra("label") != null){
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(getIntent().getStringExtra("label"));
            }

            //mProgress = (ProgressBar)findViewById(R.id.progress_web_view);
            tvLoading = (TextView)findViewById(R.id.tv_loading_webview);
            crpv = (ProgressBar) findViewById(R.id.progress_web_view);
            wv1 = (WebView)findViewById(R.id.web_view);
            wv1.getSettings().setLoadsImagesAutomatically(true);
            wv1.getSettings().setJavaScriptEnabled(true);
            wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv1.setWebViewClient(new MyBrowser());
            wv1.loadUrl(url);
            wv1.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    view.setVisibility(View.GONE);
                    crpv.setVisibility(View.VISIBLE);
                    tvLoading.setVisibility(View.VISIBLE);
                    view.loadUrl("javascript:(function() { " +
                            "var head = document.getElementsByTagName('header')[0];"
                            + "head.parentNode.removeChild(head);" +
                            "})()");
                    view.loadUrl("javascript:(function() { " +
                            "var footer = document.getElementsByTagName('footer')[0];"
                            + "footer.parentNode.removeChild(footer);" +
                            "})()");
                    view.loadUrl("javascript:(function() { " +
                            "var nav = document.getElementsByTagName('nav')[0];"
                            + "nav.parentNode.removeChild(nav);" +
                            "})()");
                    view.loadUrl("javascript:(function() { " +
                            "var set = document.getElementsByClassName('banner');"
                            + "set[0].style.margin = '0px';" +
                            "})()");
                    if(progress == 100){
                        while(!injectJavaScript(view)){
                            crpv.setVisibility(View.GONE);
                            tvProgress.setVisibility(View.GONE);
                            tvLoading.setVisibility(View.GONE);
                            view.setVisibility(View.VISIBLE);

                        }
                    }

                }
            });
            wv1.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url)
                {
                    view.loadUrl("javascript:(function() { " +
                            "var head = document.getElementsByTagName('header')[0];"
                            + "head.parentNode.removeChild(head);" +
                            "})()");
                    view.loadUrl("javascript:(function() { " +
                            "var footer = document.getElementsByTagName('footer')[0];"
                            + "footer.parentNode.removeChild(footer);" +
                            "})()");
                    view.loadUrl("javascript:(function() { " +
                            "var nav = document.getElementsByTagName('nav')[0];"
                            + "nav.parentNode.removeChild(nav);" +
                            "})()");
                    view.loadUrl("javascript:(function() { " +
                            "var set = document.getElementsByClassName('banner');"
                            + "set[0].style.margin = '0px';" +
                            "})()");
                    crpv.setVisibility(View.GONE);
                    tvLoading.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                }
            });
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(WebViewActivityNav.this).create();
            alertDialog.setTitle("No Internet");
            alertDialog.setMessage("Please Connect to internet");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Dismiss",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_web_view_activity_nav, null);
        navigationView.addHeaderView(header);
        tvUserName = (TextView)header.findViewById(R.id.tv_user_name_webview);
        tvUserEmail = (TextView)header.findViewById(R.id.tv_user_email_webview);
        tvUserName.setText(sharedPreferences.getString("user_name",""));
        tvUserEmail.setText(sharedPreferences.getString("user_email",""));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_view_activity_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            AlertDialog alertDialog = new AlertDialog.Builder(WebViewActivityNav.this).create();
            alertDialog.setTitle("Logout");
            alertDialog.setMessage("are you sure you want to logout??");
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
            return true;
        }else if(id == R.id.back){
            super.onBackPressed();
        }else if(id == R.id.refresh){
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MenuActivtyNav.class);
            intent.putExtra("id",sharedPreferences.getString("id",""));
            startActivity(intent);
        } else if (id == R.id.profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.off_take_zone) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"offtakezone.php");
            intent.putExtra("label","Off Take Zone");
            startActivity(intent);
        } else if (id == R.id.sales) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"sales.php");
            intent.putExtra("label","Sales ");
            startActivity(intent);
        } else if (id == R.id.sales_asm_ws) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"salesbyasm.php");
            intent.putExtra("label","Sales ASM WS");
            startActivity(intent);
        } else if (id == R.id.stock_on_hand) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"stock.php");
            intent.putExtra("label","Stock On Hand");
            startActivity(intent);
//            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.outstanding_ageing) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"outstanding.php");
            intent.putExtra("label","Outstanding Ageing");
            startActivity(intent);
        } else if (id == R.id.production_plan) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"productionplan.php");
            intent.putExtra("label","Production Plan");
            startActivity(intent);
//            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.qc_claim_status) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"qc.php");
            intent.putExtra("label","Quality Claim Status");
            startActivity(intent);
        } else if (id == R.id.pending_order_zone) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"pendingorderzone.php");
            intent.putExtra("label","Pending Order Zone");
            startActivity(intent);
        } else if (id == R.id.pending_order_zone_asm) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"pendingorderasm.php");
            intent.putExtra("label","Pending Order ASM");
            startActivity(intent);
        } else if (id == R.id.product_mc_details) {
//            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
//            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"prodctmcdetails.php");
//            intent.putExtra("label","Product M/C Details");
//            startActivity(intent);
            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mrp_of_products) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"mrp.php");
            intent.putExtra("label","MRP of Products");
            startActivity(intent);
//            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.landed_cost) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"landedcost.php");
            intent.putExtra("label","Landed Cost");
            startActivity(intent);
//            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.customer_details) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"customerdetails.php");
            intent.putExtra("label","Customer Details");
            startActivity(intent);
        } else if (id == R.id.order_booking) {
            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.market_visit) {
            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mop) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view",getResources().getString(R.string.base_url)+getResources().getString(R.string.path_url)+"mop.php");
            intent.putExtra("label","MOP");
            startActivity(intent);
//            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.logout_nav){
            AlertDialog alertDialog = new AlertDialog.Builder(WebViewActivityNav.this).create();
            alertDialog.setTitle("Logout");
            alertDialog.setMessage("are you sure you want to logout??");
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv1.canGoBack()) {
                        wv1.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private boolean injectJavaScript(WebView view){
        view.loadUrl("javascript:(function() { " +
                "var head = document.getElementsByTagName('header')[0];"
                + "head.parentNode.removeChild(head);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var footer = document.getElementsByTagName('footer')[0];"
                + "footer.parentNode.removeChild(footer);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var nav = document.getElementsByTagName('nav')[0];"
                + "nav.parentNode.removeChild(nav);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var set = document.getElementsByClassName('banner');"
                + "set[0].style.margin = '0px';" +
                "})()");
        return true;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}