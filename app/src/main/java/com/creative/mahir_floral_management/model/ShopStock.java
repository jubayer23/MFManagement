package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopStock extends BaseModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("shop_name")
    @Expose
    private String shopName;

    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("unit")
    @Expose
    private String unit;

    @SerializedName("received_date")
    @Expose
    private String received_date;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;

    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;

    @SerializedName("comment")
    @Expose
    private String comment;

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProductName() {
        return productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getReceived_date() {
        return received_date;
    }

    public String getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int hashCode() {
        return 31 * 21 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        ShopStock rawStock = (ShopStock) obj;

        // comparing the state of argument with
        // the state of 'this' Object.
        return (rawStock.id.equals(this.id));
    }
}
