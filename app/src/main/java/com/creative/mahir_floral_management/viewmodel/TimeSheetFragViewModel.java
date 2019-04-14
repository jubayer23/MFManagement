package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.AuthorizationApi;
import com.creative.mahir_floral_management.appdata.remote.CheckInOutApi;
import com.creative.mahir_floral_management.appdata.remote.DataWrapper;
import com.creative.mahir_floral_management.appdata.remote.TimeSheetApi;
import com.creative.mahir_floral_management.appdata.remote.UserInfoApi;
import com.creative.mahir_floral_management.model.Authorization;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.TimeSheetInfo;
import com.creative.mahir_floral_management.model.UserCheck;
import com.creative.mahir_floral_management.model.UserInfo;

import java.util.HashMap;

public class TimeSheetFragViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedWeek = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedYear = new MutableLiveData<>();

    private MutableLiveData<LoginUser> userMutableLiveData;

    public MutableLiveData<LoginUser> getUserInput() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public MutableLiveData<DataWrapper<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>>> getRemoteUserTimeSheets(int week, int year){
        TimeSheetApi timeSheetApi = new TimeSheetApi();
        return timeSheetApi.getRemoteUserTimeSheets(week, year);
    }

    public int getSelectedWeek() {

        if(selectedWeek.getValue() == null) return 0;
        return selectedWeek.getValue();
    }

    public void setSelectedWeek(int value) {
        selectedWeek.setValue(value);
    }

    public int getSelectedYear() {
        if(selectedYear.getValue() == null) return 0;
        return selectedYear.getValue();
    }

    public void setSelectedYear(int value) {
        selectedYear.setValue(value);
    }
}
