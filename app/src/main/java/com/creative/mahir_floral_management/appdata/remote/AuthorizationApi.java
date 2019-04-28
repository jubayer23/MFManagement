package com.creative.mahir_floral_management.appdata.remote;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.Authorization;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.UserCheck;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//https://proandroiddev.com/concise-error-handling-with-livedata-and-retrofit-15937ceb555b
public class AuthorizationApi {

    private MutableLiveData<DataWrapper<Authorization>> mutableLiveData;

    public MutableLiveData<DataWrapper<Authorization>> getRemoteAuthorization(final Object object) {

        mutableLiveData = new MutableLiveData<>();

        final DataWrapper<Authorization> dataWrapper = new DataWrapper<>();


       /* final JSONObject body = new JSONObject();
        try {
            body.put("email", loginUser.getEmail());
            body.put("password", loginUser.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        final String json = MydApplication.gson.toJson(object);
        try {
          final JSONObject  body = new JSONObject(json);

            final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_LOGIN,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d("DEBUG",response);

                            Authorization authorization = MydApplication.gson.fromJson(response, Authorization.class);
                            dataWrapper.setData(authorization);
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
                    //params.put("Authorization", "Bearer " + MydApplication.getInstance().getPrefManger().getAuthorizer().getAccessToken());
                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return body.toString().getBytes();
                }
            };

            req.setRetryPolicy(new DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // TODO Auto-generated method stub
            MydApplication.getInstance().addToRequestQueue(req);
        } catch (JSONException e) {
            e.printStackTrace();
            VolleyError volleyError = new VolleyError();
            dataWrapper.setApiException(volleyError);
            mutableLiveData.setValue(dataWrapper);
        }




        return mutableLiveData;

    }
}
