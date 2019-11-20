package com.creative.mahir_floral_management.appdata.remote;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.RawStock;
import com.creative.mahir_floral_management.model.RawStocks;
import com.creative.mahir_floral_management.model.ReturnStock;
import com.creative.mahir_floral_management.model.ReturnStocks;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;

public class ReturnStockApi {

    public void getReturnStock(int month, String year, String shop_id,
                            final Observer<List<ReturnStock>> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("month", month);
            body.put("year", year);
            if(!shop_id.isEmpty()) body.put("filter_by_shop_id", shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

       // Log.d("DEBUG", "its called in outside");

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_GET_RETURN_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // Log.d("DEBUG__", response);

                        try {


                            ReturnStocks returnStocks = MydApplication.gson.fromJson(response, ReturnStocks.class);
                            if(returnStocks.getStatus()){
                                //Log.d("DEBUG", "its here");
                                observer.onNext(returnStocks.getReturnStocks());
                            }else{

                                //Log.d("DEBUG", "its here 2");
                                observer.onError(new Throwable(returnStocks.getMessage()));
                            }


                        } catch (Exception e) {
                            observer.onError(e);

                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                observer.onError(error);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", MydApplication.getInstance().getPrefManger().getAccessToekn());
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

    }



    public void markReturnStockReceived(String returnStockId, String shopName,
                                      final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("return_stock_id", returnStockId);
            body.put("shop_name", shopName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_RECEIVE_RETURN_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG", response);

                        try {

                            BaseModel baseModel = new BaseModel();

                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("status")) {

                                baseModel.setStatus(true);
                                baseModel.setMessage(jsonObject.getString("message"));
                                observer.onNext(baseModel);

                            } else {

                                String msg = "Some error occur";

                                if (jsonObject.has("message"))
                                    msg = jsonObject.getString("message");

                                observer.onError(new Throwable(msg));
                            }

                        } catch (Exception e) {
                            observer.onError(e);

                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                observer.onError(error);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", MydApplication.getInstance().getPrefManger().getAccessToekn());
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

    }



}
