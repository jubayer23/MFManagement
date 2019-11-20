package com.creative.mahir_floral_management.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnStocks extends BaseModel
{


    @SerializedName("returnStocks")
    @Expose
    private List<ReturnStock> returnStocks = null;


    public ReturnStocks() {
    }



    public List<ReturnStock> getReturnStocks() {
        return returnStocks;
    }

    public void setReturnStocks(List<ReturnStock> returnStocks) {
        this.returnStocks = returnStocks;
    }



}