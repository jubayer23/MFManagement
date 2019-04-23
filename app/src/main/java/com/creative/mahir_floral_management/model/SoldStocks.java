package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoldStocks extends BaseModel{


    @SerializedName("soldStocks")
    @Expose
    private List<SoldStock> soldStocks = null;



    public List<SoldStock> getSoldStocks() {
        return soldStocks;
    }

    public void setSoldStocks(List<SoldStock> soldStocks) {
        this.soldStocks = soldStocks;
    }

}