package com.creative.mahir_floral_management.appdata.remote;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ShopStock;
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

                            List<ShopStock> data;
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("status")) {
                                Type listType = new TypeToken<ArrayList<ShopStock>>() {
                                }.getType();
                                data = MydApplication.gson.fromJson(jsonObject.getJSONArray("incomingShopStocks").toString(), listType);
                                observer.onNext(data);
                            } else {

                                String msg;

                                if (jsonObject.has("errors"))
                                    if (jsonObject.getJSONObject("errors").has("year")) {
                                        msg = jsonObject.getJSONObject("errors").getString("year");
                                    } else
                                        msg = jsonObject.getJSONObject("errors").getString("month");
                                else
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

    public void markShopStockReceived(String shopStockID,
                                      final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("shop_stock_id", shopStockID);
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

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_INCOMING_SHOP_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            List<ShopStock> data;
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("status")) {
                                Type listType = new TypeToken<ArrayList<ShopStock>>() {
                                }.getType();
                                data = MydApplication.gson.fromJson(jsonObject.getJSONArray("incomingShopStocks").toString(), listType);
                                observer.onNext(data);
                            } else {

                                String msg;

                                if (jsonObject.has("errors"))
                                    if (jsonObject.getJSONObject("errors").has("year")) {
                                        msg = jsonObject.getJSONObject("errors").getString("year");
                                    } else
                                        msg = jsonObject.getJSONObject("errors").getString("month");
                                else
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

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_SOLD_SHOP_STOCK,
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
}
