package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DemandedStocks extends BaseModel{

    @SerializedName("demandedStocks")
    @Expose
    private List<DemandedStock> demandedStocks = null;

    public List<DemandedStock> getDemandedStocks() {
        return demandedStocks;
    }

    public void setDemandedStocks(List<DemandedStock> demandedStocks) {
        this.demandedStocks = demandedStocks;
    }

}