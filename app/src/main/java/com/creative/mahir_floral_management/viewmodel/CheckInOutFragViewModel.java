package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.remote.CheckInOutApi;
import com.creative.mahir_floral_management.appdata.remote.DataWrapper;

public class CheckInOutFragViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> last_check_in = new MutableLiveData<>();
    public MutableLiveData<String> CheckInOutText = new MutableLiveData<>();

    private MutableLiveData<String> mutableLiveDataUserInput;
    private MutableLiveData<DataWrapper<Boolean>> mutableLiveDataCheckChangeStatus;
    private MutableLiveData<Boolean> mutableLiveDataShowProgressBar;

    private int user_current_check_status = 0;

    public MutableLiveData<String> getUserInput(){
        if (mutableLiveDataUserInput == null) {
            mutableLiveDataUserInput = new MutableLiveData<>();
        }
        return mutableLiveDataUserInput;
    }

    public MutableLiveData<DataWrapper<String>> getRemoteUserCurrentCheckStatus(){
        CheckInOutApi checkInOutApi = new CheckInOutApi();
        return checkInOutApi.getUserCurrentCheckStatus();
    }

    public MutableLiveData<DataWrapper<Boolean>> setRemoteUserCheckStatus(){
        CheckInOutApi checkInOutApi = new CheckInOutApi();
        return checkInOutApi.setCheckInOut(getUserCurrentCheckStatus() == 1? 0: 1);
    }


    public void setUsername(String str_username){
        username.setValue(str_username);
    }

    public void setLastCheckIn(String str_last_check_in){
        String format = CommonMethods.changeFormat(str_last_check_in,"yyyy-mm-dd hh:mm:ss" ,"dd/mm/yyyy hh:mm a");
        last_check_in.setValue(format);
    }

    public int getUserCurrentCheckStatus() {
        return user_current_check_status;
    }

    public void setUserCurrentCheckStatus(int user_current_check_status) {
        this.user_current_check_status = user_current_check_status;

        if(user_current_check_status == 1){
            CheckInOutText.setValue("Check-Out");
        }else{
            CheckInOutText.setValue("Check-In");
        }
    }

    public void onClickCheckInOut(View view){
        mutableLiveDataUserInput.setValue(password.getValue());
    }
}
