package com.creative.mahir_floral_management.appdata.remote;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.Shops;

import java.util.HashMap;
import java.util.Map;

public class ShopInfoApi {

    private MutableLiveData<DataWrapper<Shops>> mutableLiveData;

    public MutableLiveData<DataWrapper<Shops>> getRemoteShopInfo() {

        mutableLiveData = new MutableLiveData<>();

        final DataWrapper<Shops> dataWrapper = new DataWrapper<>();


       /* final JSONObject body = new JSONObject();
        try {
            body.put("email", loginUser.getEmail());
            body.put("password", loginUser.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_GET_SHOPS,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("DEBUG",response);

                        Shops shopInfo = MydApplication.gson.fromJson(response, Shops.class);
                        dataWrapper.setData(shopInfo);
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
               // params.put("Authorization",  MydApplication.getInstance().getPrefManger().getAccessToekn());
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
