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

public class ReturnStock implements Parcelable
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
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("return_date")
    @Expose
    private String returnDate;
    @SerializedName("received_date")
    @Expose
    private String receivedDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("comment")
    @Expose
    private String comment;
    public final static Parcelable.Creator<ReturnStock> CREATOR = new Creator<ReturnStock>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReturnStock createFromParcel(Parcel in) {
            return new ReturnStock(in);
        }

        public ReturnStock[] newArray(int size) {
            return (new ReturnStock[size]);
        }

    }
            ;

    protected ReturnStock(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.shopName = ((String) in.readValue((String.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.color = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.returnDate = ((String) in.readValue((String.class.getClassLoader())));
        this.receivedDate = ((String) in.readValue((Object.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ReturnStock() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        dest.writeValue(color);
        dest.writeValue(unit);
        dest.writeValue(quantity);
        dest.writeValue(returnDate);
        dest.writeValue(receivedDate);
        dest.writeValue(status);
        dest.writeValue(comment);
    }

    public int describeContents() {
        return 0;
    }

    public static class timeComparatorDesc implements Comparator<ReturnStock> {
        public int compare(ReturnStock chair1, ReturnStock chair2) {
            SimpleDateFormat readFormat = new SimpleDateFormat(GlobalAppAccess.SERVER_DATE_FORMAT, java.util.Locale.getDefault());
            try {
                java.util.Date date1 = readFormat.parse(chair1.getReturnDate());
                java.util.Date date2 = readFormat.parse(chair2.getReturnDate());
                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}
