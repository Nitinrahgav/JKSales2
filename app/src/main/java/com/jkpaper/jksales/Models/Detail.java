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
    private Integer tgt;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("sls")
    @Expose
    private Integer sls;
    @SerializedName("shortfall")
    @Expose
    private Integer shortfall;
    @SerializedName("achieved")
    @Expose
    private String achieved;
    @SerializedName("sls_last")
    @Expose
    private Integer slsLast;
    @SerializedName("sls_avg")
    @Expose
    private Integer slsAvg;
    @SerializedName("sls_ly")
    @Expose
    private Integer slsLy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTgt() {
        return tgt;
    }

    public void setTgt(Integer tgt) {
        this.tgt = tgt;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getSls() {
        return sls;
    }

    public void setSls(Integer sls) {
        this.sls = sls;
    }

    public Integer getShortfall() {
        return shortfall;
    }

    public void setShortfall(Integer shortfall) {
        this.shortfall = shortfall;
    }

    public String getAchieved() {
        return achieved;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public Integer getSlsLast() {
        return slsLast;
    }

    public void setSlsLast(Integer slsLast) {
        this.slsLast = slsLast;
    }

    public Integer getSlsAvg() {
        return slsAvg;
    }

    public void setSlsAvg(Integer slsAvg) {
        this.slsAvg = slsAvg;
    }

    public Integer getSlsLy() {
        return slsLy;
    }

    public void setSlsLy(Integer slsLy) {
        this.slsLy = slsLy;
    }

}