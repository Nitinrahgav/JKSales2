package com.jkpaper.jksales.Models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;


public interface ResponseInterface {
    @Headers({
            "Token:d75542712c868c1690110db641ba01a"
    })
    @GET
    Call<ResponseMenu> getResponse(@Url String url);
}
