package com.jkpaper.jksales.Models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface ResponseInterface {
    @Headers({
            "Token:d75542712c868c1690110db641ba01a"
    })
    @GET("/jkapi/get_menus.php")
    Call<ResponseMenu> getResponse();
}
