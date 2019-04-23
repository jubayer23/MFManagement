package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncomingShopStocks extends BaseModel{

    @SerializedName("incomingShopStocks")
    @Expose
    private List<ShopStock> incomingShopStocks = null;

    public List<ShopStock> getIncomingShopStocks() {
        return incomingShopStocks;
    }

    public void setIncomingShopStocks(List<ShopStock> incomingShopStocks) {
        this.incomingShopStocks = incomingShopStocks;
    }

}