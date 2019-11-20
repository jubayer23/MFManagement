package com.creative.mahir_floral_management.appdata.remote;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.IncomingShopStocks;
import com.creative.mahir_floral_management.model.ShopStock;
import com.creative.mahir_floral_management.model.ShopStocks;
import com.creative.mahir_floral_management.model.SoldStock;
import com.creative.mahir_floral_management.model.SoldStocks;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;

public class ShopStockAPI {

    public void getShopIncomingStock(int month, String year, int filter_by_shop_id,
                                     final Observer<List<ShopStock>> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("month", month);
            body.put("year", year);
            body.put("filter_by_shop_id", filter_by_shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_INCOMING_SHOP_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            IncomingShopStocks incomingShopStocks = MydApplication.gson.fromJson(response, IncomingShopStocks.class);
                            if(incomingShopStocks.getStatus()){
                                observer.onNext(incomingShopStocks.getIncomingShopStocks());
                                Log.d("DEBUG","its called suucess");
                            }else{
                                observer.onError(new Throwable(incomingShopStocks.getMessage()));
                            }


                        } catch (Exception e) {
                            Log.d("DEBUG","its called");
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

    public void markShopStockReceived(String shopStockID,String shopName,
                                      final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("shop_stock_id", shopStockID);
            body.put("shop_name", shopName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_MARK_SHOP_STOCK_RECEIVED,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

    public void getShopStock(int month, String year, int filter_by_shop_id,
                             final Observer<List<ShopStock>> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("month", month);
            body.put("year", year);
            body.put("filter_by_shop_id", filter_by_shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_SHOP_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            ShopStocks shopStocks = MydApplication.gson.fromJson(response, ShopStocks.class);
                            if(shopStocks.getStatus()){
                                observer.onNext(shopStocks.getShopStocks());
                            }else{
                                observer.onError(new Throwable(shopStocks.getMessage()));
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

    public void soldShopStock(String shopStockID, int quantity, String comment,
                              final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("shop_stock_id", shopStockID);
            body.put("quantity", quantity);
            body.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_ENTRY_SHOP_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            BaseModel baseModel = MydApplication.gson.fromJson(response, BaseModel.class);
                            observer.onNext(baseModel);


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

    public void getShopSoldStocks(int month, String year, int filter_by_shop_id,
                                  final Observer<List<SoldStock>> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("month", month);
            body.put("year", year);
            body.put("filter_by_shop_id", filter_by_shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_SOLD_SHOP_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            SoldStocks soldStocks = MydApplication.gson.fromJson(response, SoldStocks.class);

                            if(soldStocks.getStatus()){
                                observer.onNext(soldStocks.getSoldStocks());
                            }else{
                                observer.onError(new Throwable(soldStocks.getMessage()));
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

    public void returnShopStock(String shopStockID, String shopId, int quantity, String productId, String comment,
                              final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("shop_stock_id", shopStockID);
            body.put("shop_id", shopId);
            body.put("quantity", quantity);
            body.put("product_id", productId);
            body.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_ENTRY_RETURN_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            BaseModel baseModel = MydApplication.gson.fromJson(response, BaseModel.class);
                            observer.onNext(baseModel);


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
