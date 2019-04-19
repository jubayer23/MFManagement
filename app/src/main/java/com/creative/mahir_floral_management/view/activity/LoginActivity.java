package com.creative.mahir_floral_management.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.DeviceInfoUtils;
import com.creative.mahir_floral_management.Utility.RunnTimePermissions;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.appdata.remote.ApiObserver;
import com.creative.mahir_floral_management.appdata.remote.DataWrapper;
import com.creative.mahir_floral_management.databinding.ActivityLoginBinding;
import com.creative.mahir_floral_management.model.Authorization;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.ShopInfo;
import com.creative.mahir_floral_management.model.UserInfo;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.LoginViewModel;

import java.util.Objects;


public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MydApplication.getInstance().getPrefManger().getUserInfo() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }


        // setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);

        loginViewModel.setEmailFromCache(MydApplication.getInstance().getPrefManger().getEmailCache());


        loginViewModel.getRemoteShopInfo();

        RunnTimePermissions.requestForAllRuntimePermissions(this);

        getUserInput();
    }

    private void getUserInput() {
        loginViewModel.getUserInput().observe(this, new Observer<LoginUser>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(@Nullable LoginUser loginUser) {
                if (!RunnTimePermissions.requestForAllRuntimePermissions(LoginActivity.this)) {
                    return;
                } else if (!DeviceInfoUtils.isConnectingToInternet(LoginActivity.this)) {
                    AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "ERROR", "Please connect to a working internet connection!", false);
                    return;
                } /*else if (!DeviceInfoUtils.isGooglePlayServicesAvailable(LoginActivity.this)) {
                    AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "Warning", "This app need google play service to work properly. Please install it!!", false);
                    return;
                } */else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getEmail())) {
                    binding.edEmail.setError("Enter an E-Mail Address");
                    binding.edEmail.requestFocus();
                } else if (!loginUser.isEmailValid()) {
                    binding.edEmail.setError("Enter a Valid E-mail Address");
                    binding.edEmail.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPassword())) {
                    binding.edPassword.setError("Enter a Password");
                    binding.edPassword.requestFocus();
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.edPassword.setError("Enter at least 6 Digit password");
                    binding.edPassword.requestFocus();
                } else {
                    //binding.lblEmailAnswer.setText(loginUser.getStrEmailAddress());
                    //binding.lblPasswordAnswer.setText(loginUser.getStrPassword());
                    MydApplication.getInstance().getPrefManger().setEmailCache(loginUser.getEmail());
                    MydApplication.getInstance().getPrefManger().setUserLoginInfo(loginUser);
                    getRemoteAuthorization(loginUser);
                }
            }
        });
    }

    private void getRemoteAuthorization(LoginUser loginUser) {

        showProgressDialog("Loading...", true, false);

        loginViewModel.getRemoteAuthorization(loginUser).observe(this, new ApiObserver<Authorization>(new ApiObserver.ChangeListener<Authorization>() {
            @Override
            public void onSuccess(Authorization authorization) {
                if (authorization.getStatus()) {
                    MydApplication.getInstance().getPrefManger().setAccessToekn(authorization.getAccessToken());
                    getRemoteUserInfo();
                } else {
                    dismissProgressDialog();
                    AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "Alert", authorization.getMessage(), false);
                }
            }

            @Override
            public void onException(VolleyError volleyError) {
                dismissProgressDialog();
                AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "Alert", "Network or Server response problem. Please try again later", false);
            }
        }));

    }

    private void getRemoteUserInfo() {

        loginViewModel.getRemoteUserInfo().observe(this, new ApiObserver<UserInfo>(new ApiObserver.ChangeListener<UserInfo>() {

            @Override
            public void onSuccess(UserInfo userInfo) {


                if(userInfo.getStatus()){
                    MydApplication.getInstance().getPrefManger().setUserInfo(userInfo);
                    getRemoteShopInfo();
                }else{
                    AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this,"Alert",userInfo.getMessage(),false);
                }



            }

            @Override
            public void onException(VolleyError volleyError) {
                dismissProgressDialog();
                AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "Alert", "Network or Server response problem. Please try again later", false);

            }
        }));


    }


    private void getRemoteShopInfo() {
        loginViewModel.getRemoteShopInfo().observe(this, new ApiObserver<>(new ApiObserver.ChangeListener<ShopInfo>() {
            @Override
            public void onSuccess(ShopInfo shopInfo) {

                dismissProgressDialog();

                if (shopInfo.getStatus()) {
                    MydApplication.getInstance().getPrefManger().setShops(shopInfo.getShops());
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    //Log.d("DEBUG",userInfo.getName());
                    finish();

                } else {
                    AlertDialogForAnything.showAlertDialogWhenComplte(LoginActivity.this, "Alert", "server issue!", false);
                }
            }

            @Override
            public void onException(VolleyError volleyError) {

            }
        }));
    }
}
