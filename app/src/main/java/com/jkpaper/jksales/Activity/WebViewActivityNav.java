package com.jkpaper.jksales.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
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
    String url;
    ProgressBar mProgress;
    ProgressBar crpv;
    TextView tvProgress, tvLoading, tvUserName, tvUserEmail;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = getIntent().getStringExtra("url_web_view");
        Log.d("url",url);
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
        if (id == R.id.action_settings) {
            return true;
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
            Toast.makeText(getApplicationContext(),"To be implemented!",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.off_take_zone) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=1");
            intent.putExtra("title","Off Take Zone");
            startActivity(intent);
        } else if (id == R.id.sales) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Sales");
            startActivity(intent);
        }  else if (id == R.id.sales_asm_ws) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view","http://nitinraghav.com/jkapi/stock1.php");
            intent.putExtra("label","Stock On Hand");
            startActivity(intent);
        } else if (id == R.id.stock_on_hand) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view","http://nitinraghav.com/jkapi/stock1.php");
            intent.putExtra("label","Stock On Hand");
            startActivity(intent);
        } else if (id == R.id.outstanding_ageing) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view","http://nitinraghav.com/jkapi/outstanding.php");
            intent.putExtra("label","Outstanding Ageing");
            startActivity(intent);
        } else if (id == R.id.production_plan) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Production Plan");
            startActivity(intent);
        } else if (id == R.id.qc_claim_status) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","QC Claim Status");
            startActivity(intent);
        } else if (id == R.id.cform_status) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","CForm Status");
            startActivity(intent);
        } else if (id == R.id.pending_order_zone) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Pending Order Zone");
            startActivity(intent);
        } else if (id == R.id.pending_order_zone_asm) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Pending Order Zone / ASM");
            startActivity(intent);
        } else if (id == R.id.product_mc_details) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Product M/C Details");
            startActivity(intent);
        } else if (id == R.id.mrp_of_products) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","MRP of Products");
            startActivity(intent);
        } else if (id == R.id.landed_cost) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","landed Cost");
            startActivity(intent);
        } else if (id == R.id.consumer_details) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Consumer Details");
            startActivity(intent);
        } else if (id == R.id.order_booking) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Order Booking");
            startActivity(intent);
        } else if (id == R.id.market_visit) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Market Visit");
            startActivity(intent);
        } else if (id == R.id.mop) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","MOP");
            startActivity(intent);
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
}
