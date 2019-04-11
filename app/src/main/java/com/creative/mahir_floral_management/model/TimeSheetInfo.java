package com.creative.mahir_floral_management.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSheetInfo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public class User {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("timeSheets")
        @Expose
        private List<TimeSheet> timeSheets = null;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<TimeSheet> getTimeSheets() {
            return timeSheets;
        }

        public void setTimeSheets(List<TimeSheet> timeSheets) {
            this.timeSheets = timeSheets;
        }



    }

    public class TimeSheet {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("check_in")
        @Expose
        private String checkIn;
        @SerializedName("check_out")
        @Expose
        private String checkOut;
        @SerializedName("total_hour")
        @Expose
        private String totalHour;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(String checkIn) {
            this.checkIn = checkIn;
        }

        public String getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(String checkOut) {
            this.checkOut = checkOut;
        }

        public String getTotalHour() {
            return totalHour;
        }

        public void setTotalHour(String totalHour) {
            this.totalHour = totalHour;
        }

    }

}