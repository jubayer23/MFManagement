package com.creative.mahir_floral_management.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.remote.DataWrapper;
import com.creative.mahir_floral_management.databinding.ActivityLoginBinding;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.viewmodel.LoginViewModel;

import java.util.Objects;


public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);


        getUserInput();
    }

    private void getUserInput(){
        loginViewModel.getUserInput().observe(this, new Observer<LoginUser>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(@Nullable LoginUser loginUser) {
                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getEmail())) {
                    binding.edEmail.setError("Enter an E-Mail Address");
                    binding.edEmail.requestFocus();
                }
                else if (!loginUser.isEmailValid()) {
                    binding.edEmail.setError("Enter a Valid E-mail Address");
                    binding.edEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPassword())) {
                    binding.edPassword.setError("Enter a Password");
                    binding.edPassword.requestFocus();
                }
                else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.edPassword.setError("Enter at least 6 Digit password");
                    binding.edPassword.requestFocus();
                }
                else {
                    //binding.lblEmailAnswer.setText(loginUser.getStrEmailAddress());
                    //binding.lblPasswordAnswer.setText(loginUser.getStrPassword());
                    getRemoteAuthorization(loginUser);
                }
            }
        });
    }

    private void getRemoteAuthorization(LoginUser loginUser){

        showProgressDialog("Loading...", true,false);

        loginViewModel.getRemoteAuthorization(loginUser).observe(this, new Observer<DataWrapper<String>>() {
            @Override
            public void onChanged(@Nullable DataWrapper<String> stringDataWrapper) {

                dismissProgressDialog();

                if(stringDataWrapper.getApiException() == null){
                    Log.d("DEBUG", stringDataWrapper.getData());
                }else{
                    Log.d("DEBUG", "Error happened");
                }
            }
        });
    }
}
