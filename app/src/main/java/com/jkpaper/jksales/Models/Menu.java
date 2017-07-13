package com.jkpaper.jksales.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ashish on 20/6/17.
 */

public class Menu {
    @SerializedName("menu_access")
    @Expose
    private int menuAccess;
    @SerializedName("menu_api")
    @Expose
    private String menuApi;
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    public int getMenuAccess(){return menuAccess;}
    public void  setMenuAccess(int menuAccess){
        this.menuAccess = menuAccess;
    }
    public String getMenuApi(){return menuApi;}
    public void  setMenuApi(String menuApi){
        this.menuApi = menuApi;
    }
    public String getMenuId() {
        return menuId;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
