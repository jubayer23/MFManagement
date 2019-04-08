package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.AuthorizationApi;
import com.creative.mahir_floral_management.appdata.remote.DataWrapper;
import com.creative.mahir_floral_management.appdata.remote.UserInfoApi;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.UserInfo;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<LoginUser> userMutableLiveData;

    public MutableLiveData<LoginUser> getUserInput() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public  void setEmailFromCache(String str_email){


        email.setValue(str_email);

    }

    public void onClick(View view) {

        LoginUser loginUser = new LoginUser(email.getValue(), password.getValue());

        userMutableLiveData.setValue(loginUser);

    }

    public MutableLiveData<DataWrapper<String>> getRemoteAuthorization(LoginUser loginUser){
        AuthorizationApi authorizationApi = new AuthorizationApi();

        return  authorizationApi.getRemoteAuthorization(loginUser);

    }

    public MutableLiveData<DataWrapper<UserInfo>> getRemoteUserInfo(){
        UserInfoApi userInfoApi = new UserInfoApi();

        return  userInfoApi.getRemoteUserInfo();

    }



}
