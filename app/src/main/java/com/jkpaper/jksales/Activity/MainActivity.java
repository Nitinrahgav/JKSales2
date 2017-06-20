package com.jkpaper.jksales.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jkpaper.jksales.Adapters.MenuAdapter;
import com.jkpaper.jksales.Models.Menu;
import com.jkpaper.jksales.Models.ResponseMenu;
import com.jkpaper.jksales.Models.ResponseInterface;
import com.jkpaper.jksales.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<String> menusList = new ArrayList<>();
    List<Menu> menus = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadResponse();
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadResponse() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nitinraghav.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ResponseInterface request = retrofit.create(ResponseInterface.class);
        Call<ResponseMenu> call = request.getResponse();
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
