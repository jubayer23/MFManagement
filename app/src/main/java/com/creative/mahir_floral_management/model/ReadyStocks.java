package com.creative.mahir_floral_management.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadyStocks extends BaseModel{

    @SerializedName("readyStocks")
    @Expose
    private List<ReadyStock> readyStocks = null;

    public List<ReadyStock> getReadyStocks() {
        return readyStocks;
    }

    public void setReadyStocks(List<ReadyStock> readyStocks) {
        this.readyStocks = readyStocks;
    }

}