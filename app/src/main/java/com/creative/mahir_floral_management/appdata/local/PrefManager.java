package com.creative.mahir_floral_management.appdata.local;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.creative.mahir_floral_management.BuildConfig;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.Shop;
import com.creative.mahir_floral_management.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jubayer on 6/6/2017.
 */


public class PrefManager {
    private static final String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static Gson GSON = new Gson();
    // Sharedpref file name
    private static final String PREF_NAME = BuildConfig.APPLICATION_ID;

    private static final String KEY_EMAIL_CACHE = "key_email_cache";
    private static final String KEY_ACCESS_TOKEN = "key_access_token";
    private static final String KEY_USER_INFO = "key_user_info";
    private static final String KEY_LOGIN_INFO = "key_login_info";
    private static final String KEY_SHOPS = "key_shops";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    public void logout(){
        editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public void setEmailCache(String obj) {
        editor = pref.edit();

        editor.putString(KEY_EMAIL_CACHE, obj);

        // commit changes
        editor.commit();
    }

    public String getEmailCache() {
        return pref.getString(KEY_EMAIL_CACHE, "");
    }

    public void setAccessToekn(String obj) {
        editor = pref.edit();

        editor.putString(KEY_ACCESS_TOKEN, obj);

        // commit changes
        editor.commit();
    }

    public String getAccessToekn() {
        return pref.getString(KEY_ACCESS_TOKEN, "");
    }


    public void setUserInfo(UserInfo obj) {
        editor = pref.edit();

        String json = MydApplication.gson.toJson(obj);

        editor.putString(KEY_USER_INFO, json);

        // commit changes
        editor.commit();
    }

    public UserInfo getUserInfo() {
        String json = pref.getString(KEY_USER_INFO, "");

        if (!json.isEmpty()) {
            return MydApplication.gson.fromJson(json, UserInfo.class);
        } else
            return null;
    }


    public void setUserLoginInfo(LoginUser obj) {
        editor = pref.edit();

        if( obj == null){
            editor.putString(KEY_LOGIN_INFO, "");

            // commit changes
            editor.commit();

            return;
        }

        String json = MydApplication.gson.toJson(obj);

        editor.putString(KEY_LOGIN_INFO, json);

        // commit changes
        editor.commit();
    }

    public LoginUser getUserLoginInfo() {
        String json = pref.getString(KEY_LOGIN_INFO, "");

        if (!json.isEmpty()) {
            return MydApplication.gson.fromJson(json, LoginUser.class);
        } else
            return null;
    }


    public List<Shop> getShops() {

        List<Shop> shops = new ArrayList<>();

        String gson = pref.getString(KEY_SHOPS, "");

        if (gson.isEmpty()) return shops;

        Type type = new TypeToken<List<Shop>>() {
        }.getType();
        shops = GSON.fromJson(gson, type);

        return shops;
    }


    public void setShops(List<Shop> obj) {
        editor = pref.edit();

        editor.putString(KEY_SHOPS, GSON.toJson(obj));

        // commit changes
        editor.commit();
    }

}