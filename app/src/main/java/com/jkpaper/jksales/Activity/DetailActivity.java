package com.jkpaper.jksales.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jkpaper.jksales.Adapters.MenuAdapter;
import com.jkpaper.jksales.Models.Detail;
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

public class DetailActivity extends AppCompatActivity {
    private List<Detail> detailList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loadResponse();
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
        Call<ResponseMenu> call = request.getResponse(getIntent().getExtras().getString("url"));
        call.enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                if(response != null){
                    ResponseMenu jsonResponse = response.body();
                    Log.d("response",jsonResponse.toString());
                    detailList = jsonResponse.getResponse().getData().getDetails();
                    Log.d("List Size", String.valueOf(detailList.size()));
//                    menus = jsonResponse.getResponse().getData().getMenus();
//                    MenuAdapter adapter = new MenuAdapter(getApplicationContext(),menus);
//                    recyclerView.setAdapter(adapter);
                    //Log.d("ResponseMenu", jsonResponse.getResponse().getData().getDetails().get(1).getName());
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                Log.v("Error",t.getMessage());
            }
        });
    }
}
