package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.DataWrapper;
import com.creative.mahir_floral_management.appdata.remote.TimeSheetApi;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.TimeSheetInfo;

import java.util.HashMap;

public class TimeSheetFragViewModel extends ViewModel {

    private MutableLiveData<LoginUser> userMutableLiveData;
    private MutableLiveData<View> viewMutableLiveData;

    public MutableLiveData<String> year = new MutableLiveData<>();
    public MutableLiveData<String> month = new MutableLiveData<>();

    private TimeSheetApi timeSheetApi;


    public TimeSheetFragViewModel(){
        timeSheetApi = new TimeSheetApi();
    }

    public MutableLiveData<LoginUser> getUserInput() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public MutableLiveData<DataWrapper<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>>> getRemoteUserTimeSheets(int week, int year){
       // TimeSheetApi timeSheetApi = new TimeSheetApi();


        if(timeSheetApi ==  null){
            timeSheetApi = new TimeSheetApi();
        }
        return timeSheetApi.getRemoteUserTimeSheets(week, year);
    }




    public MutableLiveData<View> getUserButtonClickEvent(){
        if (viewMutableLiveData == null) {
            viewMutableLiveData = new MutableLiveData<>();
        }
        return viewMutableLiveData;
    }

    public void onClick(View view){
        viewMutableLiveData.setValue(view);
    }


    public void setYear(String year) {
        this.year.setValue(year);
    }

    public void setMonth(String month) {
        this.month.setValue(month);
    }
}
