package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.view.View;

public class HQFragViewModel extends ViewModel {

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
