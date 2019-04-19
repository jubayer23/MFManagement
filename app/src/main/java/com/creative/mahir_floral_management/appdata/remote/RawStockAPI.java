package com.creative.mahir_floral_management.appdata.remote;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.RawStock;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observer;

public class RawStockAPI {

    public void getRawStock(int month, String year,
                            final Observer<List<RawStock>> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("month", month);
            body.put("year", year);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_RAW_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            List<RawStock> data;
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("status")) {
                                Type listType = new TypeToken<ArrayList<RawStock>>() {
                                }.getType();
                                data = MydApplication.gson.fromJson(jsonObject.getJSONArray("rawStocks").toString(), listType);
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

    public void saveRawStock(String productName, int quantity, String unit, String color, String comment,
                             final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("product_name", productName);
            body.put("quantity", quantity);
            body.put("unit", unit);
            body.put("color", color);
            body.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_Entry_RAW_STOCK,
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

                                String msg;

                                if (jsonObject.has("errors"))
                                    if (jsonObject.getJSONObject("errors").has("product_name")) {
                                        msg = jsonObject.getJSONObject("errors").getString("product_name");
                                    } else if (jsonObject.getJSONObject("errors").has("quantity")) {
                                        msg = jsonObject.getJSONObject("errors").getString("quantity");
                                    } else if (jsonObject.getJSONObject("errors").has("unit")) {
                                        msg = jsonObject.getJSONObject("errors").getString("unit");
                                    } else if (jsonObject.getJSONObject("errors").has("color")) {
                                        msg = jsonObject.getJSONObject("errors").getString("color");
                                    } else
                                        msg = jsonObject.getJSONObject("errors").getString("comment");
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

    public void getReadyStock(int month, String year,
                              final Observer<List<RawStock>> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("month", String.format(Locale.getDefault(), "%02d", month));
            body.put("year", year);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_READY_STOCK,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            List<RawStock> data;
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("status")) {
                                Type listType = new TypeToken<ArrayList<RawStock>>() {
                                }.getType();
                                data = MydApplication.gson.fromJson(jsonObject.getJSONArray("readyStocks").toString(), listType);
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

    public void saveReadyStock(String productName, int quantity, String unit, String color, String comment,
                               int price,
                               final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("product_name", productName);
            body.put("quantity", quantity);
            body.put("unit", unit);
            body.put("color", color);
            body.put("comment", comment);
            body.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_ENTRY_READY_STOCK,
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

                                String msg;

                                if (jsonObject.has("errors"))
                                    if (jsonObject.getJSONObject("errors").has("product_name")) {
                                        msg = jsonObject.getJSONObject("errors").getString("product_name");
                                    } else if (jsonObject.getJSONObject("errors").has("quantity")) {
                                        msg = jsonObject.getJSONObject("errors").getString("quantity");
                                    } else if (jsonObject.getJSONObject("errors").has("unit")) {
                                        msg = jsonObject.getJSONObject("errors").getString("unit");
                                    } else if (jsonObject.getJSONObject("errors").has("color")) {
                                        msg = jsonObject.getJSONObject("errors").getString("color");
                                    } else
                                        msg = jsonObject.getJSONObject("errors").getString("comment");
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

    public void deliverReadyStock(String productID, int quantity, String shopID, String comment,
                               final Observer<BaseModel> observer) {

        final JSONObject body = new JSONObject();
        try {
            body.put("shop_id", shopID);
            body.put("quantity", quantity);
            body.put("product_id", productID);
            body.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_DELIVER_READY_STOCK,
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

                                String msg;

                                if (jsonObject.has("errors"))
                                    if (jsonObject.getJSONObject("errors").has("product_name")) {
                                        msg = jsonObject.getJSONObject("errors").getString("product_name");
                                    } else if (jsonObject.getJSONObject("errors").has("quantity")) {
                                        msg = jsonObject.getJSONObject("errors").getString("quantity");
                                    } else if (jsonObject.getJSONObject("errors").has("unit")) {
                                        msg = jsonObject.getJSONObject("errors").getString("unit");
                                    } else if (jsonObject.getJSONObject("errors").has("color")) {
                                        msg = jsonObject.getJSONObject("errors").getString("color");
                                    } else
                                        msg = jsonObject.getJSONObject("errors").getString("comment");
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

}