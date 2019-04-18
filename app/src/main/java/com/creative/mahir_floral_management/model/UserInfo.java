package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_profile")
    @Expose
    private UserProfile userProfile;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public class UserProfile {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("shop_id")
        @Expose
        private String shopId;
        @SerializedName("shop_name")
        @Expose
        private String shopName;
        @SerializedName("shop_address")
        @Expose
        private String shopAddress;
        @SerializedName("shop_lat")
        @Expose
        private String shopLat;
        @SerializedName("shop_long")
        @Expose
        private String shopLong;

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public double getShopLat() {
            return Double.parseDouble(shopLat);
        }

        public void setShopLat(String shopLat) {
            this.shopLat = shopLat;
        }

        public double getShopLong() {
            return Double.parseDouble(shopLong);
        }

        public void setShopLong(String shopLong) {
            this.shopLong = shopLong;
        }

    }

}