package com.creative.mahir_floral_management.appdata.remote;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoApi {

    private MutableLiveData<DataWrapper<UserInfo>> mutableLiveData;

    public MutableLiveData<DataWrapper<UserInfo>> getRemoteUserInfo() {

        mutableLiveData = new MutableLiveData<>();

        final DataWrapper<UserInfo> dataWrapper = new DataWrapper<UserInfo>();


       /* final JSONObject body = new JSONObject();
        try {
            body.put("email", loginUser.getEmail());
            body.put("password", loginUser.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_USER_INFO,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean status = jsonObject.getBoolean("status");
                            dataWrapper.setErrorMessage(jsonObject.getString("message"));

                            if(status){
                                UserInfo userInfo = MydApplication.gson.fromJson(String.valueOf(jsonObject.getJSONObject("user_info")), UserInfo.class);
                                dataWrapper.setData(userInfo);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //dataWrapper.setData(response);
                        mutableLiveData.setValue(dataWrapper);

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dataWrapper.setApiException(error);
                mutableLiveData.setValue(dataWrapper);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization",  MydApplication.getInstance().getPrefManger().getAccessToekn());
                return params;
            }


        };

        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        MydApplication.getInstance().addToRequestQueue(req);


        return mutableLiveData;

    }
}
