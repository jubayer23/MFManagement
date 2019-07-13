package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.AuthorizationApi;
import com.creative.mahir_floral_management.appdata.remote.DataWrapper;
import com.creative.mahir_floral_management.appdata.remote.ShopInfoApi;
import com.creative.mahir_floral_management.appdata.remote.UserInfoApi;
import com.creative.mahir_floral_management.model.Authorization;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.Shops;
import com.creative.mahir_floral_management.model.UserInfo;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<LoginUser> userMutableLiveData;
    private MutableLiveData<DataWrapper<Shops>> shopInfoMutableLiveData;

    private AuthorizationApi authorizationApi;
    private UserInfoApi userInfoApi;
    private ShopInfoApi shopInfoApi;

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


    public MutableLiveData<DataWrapper<Authorization>> getRemoteAuthorization(LoginUser loginUser){
        if(authorizationApi == null){
            authorizationApi = new AuthorizationApi();
        }
        return  authorizationApi.getRemoteAuthorization(loginUser);
    }

    public MutableLiveData<DataWrapper<UserInfo>> getRemoteUserInfo(){
        if(userInfoApi == null){
            userInfoApi = new UserInfoApi();
        }
        return  userInfoApi.getRemoteUserInfo();
    }


    public MutableLiveData<DataWrapper<Shops>> getRemoteShopInfo(){
        if(shopInfoApi == null){
            shopInfoApi = new ShopInfoApi();
        }

        if(shopInfoMutableLiveData == null){
            shopInfoMutableLiveData = shopInfoApi.getRemoteShopInfo();
        }

        return shopInfoMutableLiveData;
    }



}
