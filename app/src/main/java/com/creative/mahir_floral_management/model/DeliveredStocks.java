package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveredStocks extends BaseModel{

    @SerializedName("deliveredStocks")
    @Expose
    private List<DeliveredStock> deliveredStocks = null;

    public List<DeliveredStock> getDeliveredStocks() {
        return deliveredStocks;
    }

    public void setDeliveredStocks(List<DeliveredStock> deliveredStocks) {
        this.deliveredStocks = deliveredStocks;
    }

}