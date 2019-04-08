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

public class CheckInOutApi {

    private MutableLiveData<DataWrapper<Boolean>> mutableLiveDataSetCheck;
    public MutableLiveData<DataWrapper<Boolean>> setCheckInOut(int check_status) {


        mutableLiveDataSetCheck = new MutableLiveData<>();

        final DataWrapper<Boolean> dataWrapper = new DataWrapper<Boolean>();


        //Log.d("DEBUG_check_status", String.valueOf(check_status));

        final JSONObject body = new JSONObject();
        try {
            body.put("user_id", MydApplication.getInstance().getPrefManger().getUserInfo().getId());
            body.put("user_check",check_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_SET_USER_CHECK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("DEBUG",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean status = jsonObject.getBoolean("status");
                            dataWrapper.setErrorMessage(jsonObject.getString("message"));

                            if(status){

                                dataWrapper.setData(status);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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



    private MutableLiveData<DataWrapper<String>> mutableLiveData;
    public MutableLiveData<DataWrapper<String>> getUserCurrentCheckStatus() {


        mutableLiveData = new MutableLiveData<>();

        final DataWrapper<String> dataWrapper = new DataWrapper<>();


        final JSONObject body = new JSONObject();
        try {
            body.put("user_id", MydApplication.getInstance().getPrefManger().getUserInfo().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_GET_USER_CHECK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean status = jsonObject.getBoolean("status");
                            dataWrapper.setErrorMessage(jsonObject.getString("message"));

                            if(status){

                                String is_online = jsonObject.getString("Online");

                                if(is_online.equals("1")){
                                    String last_check_in = jsonObject.getString("last_checked_in");
                                    dataWrapper.setData(is_online + "/" + last_check_in);
                                }else{
                                    dataWrapper.setData(jsonObject.getString("Online") );
                                }


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
