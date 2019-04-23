package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopStocks extends BaseModel{


    @SerializedName("shopStocks")
    @Expose
    private List<ShopStock> shopStocks = null;



    public List<ShopStock> getShopStocks() {
        return shopStocks;
    }

    public void setShopStocks(List<ShopStock> shopStocks) {
        this.shopStocks = shopStocks;
    }

}