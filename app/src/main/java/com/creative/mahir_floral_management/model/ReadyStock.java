package com.creative.mahir_floral_management.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadyStock implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("received_date")
    @Expose
    private String receivedDate;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    public final static Parcelable.Creator<ReadyStock> CREATOR = new Creator<ReadyStock>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReadyStock createFromParcel(Parcel in) {
            return new ReadyStock(in);
        }

        public ReadyStock[] newArray(int size) {
            return (new ReadyStock[size]);
        }

    };

    protected ReadyStock(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.receivedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.color = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.addedBy = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ReadyStock() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(quantity);
        dest.writeValue(price);
        dest.writeValue(unit);
        dest.writeValue(receivedDate);
        dest.writeValue(color);
        dest.writeValue(comment);
        dest.writeValue(addedBy);
    }

    public int describeContents() {
        return 0;
    }
}