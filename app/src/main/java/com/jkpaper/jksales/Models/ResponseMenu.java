package com.jkpaper.jksales.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ashish on 20/6/17.
 */
public class ResponseMenu {

    @SerializedName("Response")
    @Expose
    private Response_ response;

    public Response_ getResponse() {
        return response;
    }

    public void setResponse(Response_ response) {
        this.response = response;
    }

}