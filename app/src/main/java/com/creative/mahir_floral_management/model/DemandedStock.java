package com.creative.mahir_floral_management.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DemandedStock implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("demanded_quantity")
    @Expose
    private String demandedQuantity;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("demanded_date")
    @Expose
    private String demandedDate;
    @SerializedName("demanded_shop_name")
    @Expose
    private String demandedShopName;
    public final static Parcelable.Creator<DemandedStock> CREATOR = new Creator<DemandedStock>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DemandedStock createFromParcel(Parcel in) {
            return new DemandedStock(in);
        }

        public DemandedStock[] newArray(int size) {
            return (new DemandedStock[size]);
        }

    }
            ;

    protected DemandedStock(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.color = ((String) in.readValue((String.class.getClassLoader())));
        this.demandedQuantity = ((String) in.readValue((String.class.getClassLoader())));
        this.priority = ((String) in.readValue((String.class.getClassLoader())));
        this.demandedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.demandedShopName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DemandedStock() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDemandedQuantity() {
        return demandedQuantity;
    }

    public void setDemandedQuantity(String demandedQuantity) {
        this.demandedQuantity = demandedQuantity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDemandedDate() {
        return demandedDate;
    }

    public void setDemandedDate(String demandedDate) {
        this.demandedDate = demandedDate;
    }

    public String getDemandedShopName() {
        return demandedShopName;
    }

    public void setDemandedShopName(String demandedShopName) {
        this.demandedShopName = demandedShopName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(productName);
        dest.writeValue(unit);
        dest.writeValue(color);
        dest.writeValue(demandedQuantity);
        dest.writeValue(priority);
        dest.writeValue(demandedDate);
        dest.writeValue(demandedShopName);
    }

    public int describeContents() {
        return 0;
    }

    public static class timeComparatorDesc implements Comparator<DemandedStock> {
        public int compare(DemandedStock chair1, DemandedStock chair2) {
            SimpleDateFormat readFormat = new SimpleDateFormat(GlobalAppAccess.SERVER_DATE_FORMAT, java.util.Locale.getDefault());
            try {
                java.util.Date date1 = readFormat.parse(chair1.getDemandedDate());
                java.util.Date date2 = readFormat.parse(chair2.getDemandedDate());
                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

}