package com.jkpaper.jksales.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashish on 20/6/17.
 */

public class Data {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("menus")
    @Expose
    private List<Menu> menus = null;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
