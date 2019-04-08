package com.creative.mahir_floral_management.appdata.remote;

import com.android.volley.VolleyError;

public class DataWrapper<T> {
    private  boolean status;
    private String errorMessage;
    private VolleyError volleyError;
    private T data;

    public VolleyError getApiException() {
        return volleyError;
    }

    public void setApiException(VolleyError apiException) {
        this.volleyError = apiException;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}