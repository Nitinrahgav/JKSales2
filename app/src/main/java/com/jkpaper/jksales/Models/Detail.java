package com.jkpaper.jksales.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ashish on 22/6/17.
 */

public class Detail {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tgt")
    @Expose
    private String tgt;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("sls")
    @Expose
    private String sls;
    @SerializedName("shortfall")
    @Expose
    private String shortfall;
    @SerializedName("achieved")
    @Expose
    private String achieved;
    @SerializedName("sls_last")
    @Expose
    private String slsLast;
    @SerializedName("sls_avg")
    @Expose
    private String slsAvg;
    @SerializedName("sls_ly")
    @Expose
    private String slsLy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTgt() {
        return tgt;
    }

    public void setTgt(String tgt) {
        this.tgt = tgt;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSls() {
        return sls;
    }

    public void setSls(String sls) {
        this.sls = sls;
    }

    public String getShortfall() {
        return shortfall;
    }

    public void setShortfall(String shortfall) {
        this.shortfall = shortfall;
    }

    public String getAchieved() {
        return achieved;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public String getSlsLast() {
        return slsLast;
    }

    public void setSlsLast(String slsLast) {
        this.slsLast = slsLast;
    }

    public String getSlsAvg() {
        return slsAvg;
    }

    public void setSlsAvg(String slsAvg) {
        this.slsAvg = slsAvg;
    }

    public String getSlsLy() {
        return slsLy;
    }

    public void setSlsLy(String slsLy) {
        this.slsLy = slsLy;
    }

}