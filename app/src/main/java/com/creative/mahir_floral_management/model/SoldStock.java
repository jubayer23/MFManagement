package com.creative.mahir_floral_management.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class SoldStock implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("sold_date")
    @Expose
    private String soldDate;
    @SerializedName("comment")
    @Expose
    private String comment;
    public final static Parcelable.Creator<SoldStock> CREATOR = new Creator<SoldStock>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SoldStock createFromParcel(Parcel in) {
            return new SoldStock(in);
        }

        public SoldStock[] newArray(int size) {
            return (new SoldStock[size]);
        }

    }
            ;

    protected SoldStock(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.shopName = ((String) in.readValue((String.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.soldDate = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SoldStock() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(shopName);
        dest.writeValue(productName);
        dest.writeValue(price);
        dest.writeValue(unit);
        dest.writeValue(quantity);
        dest.writeValue(soldDate);
        dest.writeValue(comment);
    }

    public int describeContents() {
        return 0;
    }

    public static class timeComparatorDesc implements Comparator<SoldStock> {
        public int compare(SoldStock chair1, SoldStock chair2) {
            SimpleDateFormat readFormat = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
            try {
                java.util.Date date1 = readFormat.parse(chair1.getSoldDate());
                java.util.Date date2 = readFormat.parse(chair2.getSoldDate());
                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

}