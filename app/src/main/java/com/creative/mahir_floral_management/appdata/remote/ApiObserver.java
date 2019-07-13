package com.creative.mahir_floral_management.appdata.remote;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;

import com.android.volley.VolleyError;

public class ApiObserver<T> implements Observer<DataWrapper<T>> {
    private ChangeListener<T> changeListener;
    private static final int ERROR_CODE = 0;
    public ApiObserver(ChangeListener<T> changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void onChanged(@Nullable DataWrapper<T> tDataWrapper) {
        if (tDataWrapper != null) {
            if (tDataWrapper.getApiException() != null) {
                changeListener.onException(tDataWrapper.getApiException());
            }else {
                changeListener.onSuccess(tDataWrapper.getData());
            }
            return;
        }
        //custom exceptionn to suite my use case
        //changeListener.onException(new ValidationAPIException(ERROR_CODE, null));
        //TODO
    }
    public interface ChangeListener<T> {
        void onSuccess(T dataWrapper);
        void onException(VolleyError volleyError);
    }
}