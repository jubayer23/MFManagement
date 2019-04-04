package com.creative.mahir_floral_management.appdata.remote;

import com.android.volley.VolleyError;

public class DataWrapper<T> {
    private VolleyError volleyError;
    private T data;

    public Exception getApiException() {
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
}