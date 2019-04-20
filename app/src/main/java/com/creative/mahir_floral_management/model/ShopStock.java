package com.creative.mahir_floral_management.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopStock extends BaseModel implements Parcelable {

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

    @SerializedName("receiver_name")
    @Expose
    private String receiverName;

    @SerializedName("receive_date")
    @Expose
    private String receiveDate;

    @SerializedName("deliver_date")
    @Expose
    private String deliverDate;

    @SerializedName("sold_date")
    @Expose
    private String soldDate;

    protected ShopStock(Parcel in) {
        id = in.readString();
        shopName = in.readString();
        productName = in.readString();
        quantity = in.readString();
        unit = in.readString();
        received_date = in.readString();
        price = in.readString();
        color = in.readString();
        deliveryDate = in.readString();
        deliveryStatus = in.readString();
        comment = in.readString();
        receiverName = in.readString();
        receiveDate = in.readString();
        deliverDate = in.readString();
        soldDate = in.readString();
    }

    public static final Creator<ShopStock> CREATOR = new Creator<ShopStock>() {
        @Override
        public ShopStock createFromParcel(Parcel in) {
            return new ShopStock(in);
        }

        @Override
        public ShopStock[] newArray(int size) {
            return new ShopStock[size];
        }
    };

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

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shopName);
        dest.writeString(productName);
        dest.writeString(quantity);
        dest.writeString(unit);
        dest.writeString(received_date);
        dest.writeString(price);
        dest.writeString(color);
        dest.writeString(deliveryDate);
        dest.writeString(deliveryStatus);
        dest.writeString(comment);
        dest.writeString(receiverName);
        dest.writeString(receiveDate);
        dest.writeString(deliverDate);
        dest.writeString(soldDate);
    }
}
