package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.view.View;

import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.remote.CheckInOutApi;
import com.creative.mahir_floral_management.appdata.remote.DataWrapper;
import com.creative.mahir_floral_management.model.UserCheck;
import com.creative.mahir_floral_management.model.UserInfo;

public class CheckInOutFragViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> last_check_in = new MutableLiveData<>();
    public MutableLiveData<String> shop_name = new MutableLiveData<>();
    public MutableLiveData<String> CheckInOutText = new MutableLiveData<>();

    private MutableLiveData<String> mutableLiveDataUserInput;
    private MutableLiveData<DataWrapper<Boolean>> mutableLiveDataCheckChangeStatus;
    private MutableLiveData<Boolean> mutableLiveDataShowProgressBar;

    private CheckInOutApi checkInOutApi;

    private int user_current_check_status = 0;

    public MutableLiveData<String> getUserInput(){
        if (mutableLiveDataUserInput == null) {
            mutableLiveDataUserInput = new MutableLiveData<>();
        }
        return mutableLiveDataUserInput;
    }

    public MutableLiveData<DataWrapper<UserCheck>> getRemoteUserCurrentCheckStatus(){
        CheckInOutApi checkInOutApi = new CheckInOutApi();
        return checkInOutApi.getUserCurrentCheckStatus();
    }

    public MutableLiveData<DataWrapper<UserCheck>> setRemoteCheckInOut(){

        if(checkInOutApi == null){
            checkInOutApi = new CheckInOutApi();
        }
        return checkInOutApi.setCheckInOut(getUserCurrentCheckStatus() == 1? 0: 1);
    }


    public void setUserProfile(UserInfo.UserProfile userProfile){
        username.setValue(userProfile.getName());
        shop_name.setValue(userProfile.getShopName());
    }

    public void setLastCheckIn(String str_last_check_in){
        if(str_last_check_in == null){
            last_check_in.setValue("You are not checked in yet.");
            return;
        }
        String format = CommonMethods.changeFormat(str_last_check_in, GlobalAppAccess.SERVER_DATE_FORMAT,"dd/MM/yyyy hh:mm a");
        last_check_in.setValue(format);
    }

    public int getUserCurrentCheckStatus() {
        return user_current_check_status;
    }

    public void setUserCurrentCheckStatus(int user_current_check_status, String last_Checked_in) {
        this.user_current_check_status = user_current_check_status;

        if(user_current_check_status == 1){
            CheckInOutText.setValue("Check-Out");

        }else{
            CheckInOutText.setValue("Check-In");
        }

        setLastCheckIn(last_Checked_in);
    }

    public void onClickCheckInOut(View view){
        mutableLiveDataUserInput.setValue(password.getValue());
    }
}
