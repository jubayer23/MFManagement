package com.creative.mahir_floral_management.appdata.remote;

import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.UserCheck;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckInOutApi {

    private MutableLiveData<DataWrapper<UserCheck>> mutableLiveDataSetCheck;
    public MutableLiveData<DataWrapper<UserCheck>> setCheckInOut(int check_status) {


        mutableLiveDataSetCheck = new MutableLiveData<>();

        final DataWrapper<UserCheck> dataWrapper = new DataWrapper<>();


        Log.d("DEBUG_check_status", String.valueOf(check_status));

        final JSONObject body = new JSONObject();
        try {
            body.put("user_id", MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getId());
            body.put("user_check",check_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_SET_USER_CHECK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG",response);

                        /*try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean status = jsonObject.getBoolean("status");
                            dataWrapper.setErrorMessage(jsonObject.getString("message"));

                            if(status){

                                dataWrapper.setData(status);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        UserCheck userCheck = MydApplication.gson.fromJson(response, UserCheck.class);
                        dataWrapper.setData(userCheck);
                        //dataWrapper.setData(response);
                        mutableLiveDataSetCheck.setValue(dataWrapper);

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dataWrapper.setApiException(error);
                mutableLiveDataSetCheck.setValue(dataWrapper);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization",  MydApplication.getInstance().getPrefManger().getAccessToekn());
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


        return mutableLiveDataSetCheck;

    }



    private MutableLiveData<DataWrapper<UserCheck>> mutableLiveData;
    public MutableLiveData<DataWrapper<UserCheck>> getUserCurrentCheckStatus() {


        mutableLiveData = new MutableLiveData<>();

        final DataWrapper<UserCheck> dataWrapper = new DataWrapper<>();


        final JSONObject body = new JSONObject();
        try {
            body.put("user_id", MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_GET_USER_CHECK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG",response);

                       /* try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean status = jsonObject.getBoolean("status");
                            dataWrapper.setErrorMessage(jsonObject.getString("message"));

                            if(status){

                                UserCheck userCheck = MydApplication.gson.fromJson(response, UserCheck.class);
                                dataWrapper.setData(userCheck);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        UserCheck userCheck = MydApplication.gson.fromJson(response, UserCheck.class);
                        dataWrapper.setData(userCheck);

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
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.toString().getBytes();
            }

        };

        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        MydApplication.getInstance().addToRequestQueue(req);


        return mutableLiveData;

    }


}
