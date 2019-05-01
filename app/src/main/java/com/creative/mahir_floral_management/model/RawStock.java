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

public class RawStock implements Parcelable {

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
    private String receivedDate;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("received_by")
    @Expose
    private String receivedBy;
    public final static Parcelable.Creator<RawStock> CREATOR = new Creator<RawStock>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RawStock createFromParcel(Parcel in) {
            return new RawStock(in);
        }

        public RawStock[] newArray(int size) {
            return (new RawStock[size]);
        }

    };

    protected RawStock(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.receivedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.color = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.receivedBy = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RawStock() {
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

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(quantity);
        dest.writeValue(unit);
        dest.writeValue(receivedDate);
        dest.writeValue(color);
        dest.writeValue(comment);
        dest.writeValue(receivedBy);
    }

    public int describeContents() {
        return 0;
    }

    public static class timeComparatorDesc implements Comparator<RawStock> {
        public int compare(RawStock chair1, RawStock chair2) {
            SimpleDateFormat readFormat = new SimpleDateFormat(GlobalAppAccess.SERVER_DATE_FORMAT, java.util.Locale.getDefault());
            try {
                java.util.Date date1 = readFormat.parse(chair1.getReceivedDate());
                java.util.Date date2 = readFormat.parse(chair2.getReceivedDate());
                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

}


