package com.jkpaper.jksales.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jkpaper.jksales.Adapters.MenuAdapter;
import com.jkpaper.jksales.Models.ResponseInterface;
import com.jkpaper.jksales.Models.ResponseMenu;
import com.jkpaper.jksales.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivtyNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<String> menusList = new ArrayList<>();
    List<com.jkpaper.jksales.Models.Menu> menus = new ArrayList<>();
    RecyclerView recyclerView;
    public String url;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activty_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initViews();
        loadResponse();
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (exit) {
            finishAffinity();
            // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;

        }
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activty_nav, menu);
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
        } else if (id == R.id.sales_asm_ws) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view","http://nitinraghav.com/jkapi/stock1.php");
            intent.putExtra("label","Stock On Hand");
            startActivity(intent);
        } else if (id == R.id.stock_on_hand) {
            Intent intent = new Intent(getApplicationContext(), WebViewActivityNav.class);
            intent.putExtra("url_web_view","http://nitinraghav.com/jkapi/stock.php");
            intent.putExtra("label","Stock On Hand");
            startActivity(intent);
        } else if (id == R.id.outstanding_ageing) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("url","http://nitinraghav.com/jkapi/get_details.php?menu_id=2");
            intent.putExtra("title","Outstanding Ageing");
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
    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadResponse() {
        url = "http://www.nitinraghav.com/jkapi/get_menus.php?user_id="+getIntent().getExtras().getString("id");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nitinraghav.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ResponseInterface request = retrofit.create(ResponseInterface.class);
        Call<ResponseMenu> call = request.getResponse(url);
        call.enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                if(response != null){
                    ResponseMenu jsonResponse = response.body();
                    menus = jsonResponse.getResponse().getData().getMenus();
                    MenuAdapter adapter = new MenuAdapter(getApplicationContext(),menus);
                    recyclerView.setAdapter(adapter);
                    Log.d("ResponseMenu", String.valueOf(jsonResponse.getResponse().getData().getMenus().get(0).getMenuName()));
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                Log.v("Error",t.getMessage());
            }
        });
    }
}
