package com.creative.mahir_floral_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCheck {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Online")
    @Expose
    private String online;
    @SerializedName("last_checked_in")
    @Expose
    private String lastCheckedIn;
    @SerializedName("last_checked_out")
    @Expose
    private Object lastCheckedOut;

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

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getLastCheckedIn() {
        return lastCheckedIn;
    }

    public void setLastCheckedIn(String lastCheckedIn) {
        this.lastCheckedIn = lastCheckedIn;
    }

    public Object getLastCheckedOut() {
        return lastCheckedOut;
    }

    public void setLastCheckedOut(Object lastCheckedOut) {
        this.lastCheckedOut = lastCheckedOut;
    }

}
