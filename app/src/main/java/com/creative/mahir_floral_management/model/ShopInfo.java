package com.creative.mahir_floral_management.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopInfo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("shops")
    @Expose
    private List<Shop> shops = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public class Shop {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("loc_lat")
        @Expose
        private String locLat;
        @SerializedName("loc_long")
        @Expose
        private String locLong;
        @SerializedName("added_by")
        @Expose
        private String addedBy;
        @SerializedName("creation_date")
        @Expose
        private String creationDate;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocLat() {
            return locLat;
        }

        public void setLocLat(String locLat) {
            this.locLat = locLat;
        }

        public String getLocLong() {
            return locLong;
        }

        public void setLocLong(String locLong) {
            this.locLong = locLong;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

    }
}