package com.creative.mahir_floral_management.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RawStock extends BaseModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

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

    protected RawStock(Parcel in) {
        id = in.readString();
        name = in.readString();
        quantity = in.readString();
        unit = in.readString();
        received_date = in.readString();
        price = in.readString();
    }

    public static final Creator<RawStock> CREATOR = new Creator<RawStock>() {
        @Override
        public RawStock createFromParcel(Parcel in) {
            return new RawStock(in);
        }

        @Override
        public RawStock[] newArray(int size) {
            return new RawStock[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    @Override
    public int hashCode() {
        return 31 * 20 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        RawStock rawStock = (RawStock) obj;

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
        dest.writeString(name);
        dest.writeString(quantity);
        dest.writeString(unit);
        dest.writeString(received_date);
        dest.writeString(price);

    }
}
