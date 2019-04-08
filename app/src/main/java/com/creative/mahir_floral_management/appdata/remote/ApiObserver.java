package com.creative.mahir_floral_management.appdata.remote;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

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
            }else if(tDataWrapper.getData() != null){
                changeListener.onSuccess(tDataWrapper.getData());
            }else{
                changeListener.onFailure(tDataWrapper.getErrorMessage());
            }
            return;
        }
        //custom exceptionn to suite my use case
        //changeListener.onException(new ValidationAPIException(ERROR_CODE, null));
        //TODO
    }
    public interface ChangeListener<T> {
        void onSuccess(T dataWrapper);
        void onFailure(String failureMessage);
        void onException(VolleyError volleyError);
    }
}