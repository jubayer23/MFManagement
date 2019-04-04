package com.creative.mahir_floral_management.model;

import android.util.Log;
import android.util.Patterns;


public class LoginUser {

    private String email;
    private String password;

    public LoginUser(String EmailAddress, String Password) {
        email = EmailAddress;
        password = Password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }


}
