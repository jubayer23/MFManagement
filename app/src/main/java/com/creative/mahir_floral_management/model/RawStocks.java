package com.creative.mahir_floral_management.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RawStocks extends BaseModel{


    @SerializedName("rawStocks")
    @Expose
    private List<RawStock> rawStocks = null;



    public List<RawStock> getRawStocks() {
        return rawStocks;
    }

    public void setRawStocks(List<RawStock> rawStocks) {
        this.rawStocks = rawStocks;
    }

}