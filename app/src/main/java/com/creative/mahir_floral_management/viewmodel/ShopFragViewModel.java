package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

public class ShopFragViewModel extends ViewModel {

    private MutableLiveData<View> viewMutableLiveData;

    public MutableLiveData<View> getUserButtonClickEvent(){
        if (viewMutableLiveData == null) {
            viewMutableLiveData = new MutableLiveData<>();
        }
        return viewMutableLiveData;
    }

    public void onClick(View view){
        viewMutableLiveData.setValue(view);
    }
}
