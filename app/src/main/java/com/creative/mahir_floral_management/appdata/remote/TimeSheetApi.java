package com.creative.mahir_floral_management.appdata.remote;

import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.TimeSheetInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSheetApi {

    String key = "";

    private MutableLiveData<DataWrapper<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>>> mutableLiveData;

    public MutableLiveData<DataWrapper<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>>> getRemoteUserTimeSheets(int week, int year) {

        mutableLiveData = new MutableLiveData<>();

        final DataWrapper<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>> dataWrapper = new DataWrapper<>();


        final JSONObject body = new JSONObject();
        try {
            body.put("filter_type", "week");
            body.put("week_num", week);
            body.put("year", year);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*Cache cache = MydApplication.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get("hello");
        if (entry != null) {

            try {
                String data = new String(entry.data, "UTF-8");
                Log.d("DEBUG_CACHE", data);
                // Get JSON from the data
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            //Make network call

            Log.d("DEBUG", "No cache at all");
        }*/

        CacheRequest cacheRequest = new CacheRequest(Request.Method.POST, APIUrl.URL_USER_TIMESHEET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response);

                TimeSheetInfo timeSheetInfo = MydApplication.gson.fromJson(response, TimeSheetInfo.class);
                HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> map;
                if (timeSheetInfo.getStatus()) {
                    map = parseData(timeSheetInfo);
                } else {
                    map = new HashMap<>();
                    map.clear();
                }


                dataWrapper.setData(map);
                //dataWrapper.setData(response);
                mutableLiveData.setValue(dataWrapper);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // mTextView.setText(error.toString());
                dataWrapper.setApiException(error);
                mutableLiveData.setValue(dataWrapper);
            }
        }){
            @Override
            public String getCacheKey() {
                String temp = super.getCacheKey();
               // Log.d("DEBUG_KEY_DETAG", temp);
                temp = MydApplication.gson.toJson(body);
                /*for (Map.Entry<String, String> entry : mParams.entrySet())
                    temp += entry.getKey() + "=" + entry.getValue();// not do another request*/
                return temp ;
            }

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

        cacheRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        MydApplication.getInstance().addToRequestQueue(cacheRequest);


        /*final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_USER_TIMESHEET,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG", response);

                       *//* try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean status = jsonObject.getBoolean("status");
                            dataWrapper.setErrorMessage(jsonObject.getString("message"));

                            if(status){
                                UserInfo userInfo = MydApplication.gson.fromJson(String.valueOf(jsonObject.getJSONObject("user_info")), UserInfo.class);
                                dataWrapper.setData(userInfo);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*//*


                        TimeSheetInfo timeSheetInfo = MydApplication.gson.fromJson(response, TimeSheetInfo.class);
                        HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> map;
                        if (timeSheetInfo.getStatus()) {
                            map = parseData(timeSheetInfo);
                        } else {
                            map = new HashMap<>();
                            map.clear();
                        }


                        dataWrapper.setData(map);
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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(jsonString, cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            public String getCacheKey() {
                String temp = super.getCacheKey();
                Log.d("DEBUG_KEY_DETAG", temp);
                temp += MydApplication.gson.toJson(body);
                *//*for (Map.Entry<String, String> entry : mParams.entrySet())
                    temp += entry.getKey() + "=" + entry.getValue();// not do another request*//*
                return "hello";
            }

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
        MydApplication.getInstance().addToRequestQueue(req);*/


        return mutableLiveData;

    }


    private HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> parseData(TimeSheetInfo
                                                                                        timeSheetInfo) {

        HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> mapUserTimeSheet = new HashMap<>();
        mapUserTimeSheet.clear();

        if (timeSheetInfo.getUsers() == null || timeSheetInfo.getUsers().isEmpty()) {

            return mapUserTimeSheet;

        }

        List<TimeSheetInfo.User> users = timeSheetInfo.getUsers();


        for (TimeSheetInfo.User user : users) {
            String name = user.getUserName();

            HashMap<String, TimeSheetInfo.TimeSheet> mapTimeSheet = new HashMap<>();

            for (TimeSheetInfo.TimeSheet timeSheet : user.getTimeSheets()) {
                String date = timeSheet.getDate();
                String chengeDateFormat = CommonMethods.changeFormat(date, "dd/MM/yyyy", "dd MMM yyyy");


                mapTimeSheet.put(chengeDateFormat, timeSheet);

            }

            mapUserTimeSheet.put(name, mapTimeSheet);
        }

        return mapUserTimeSheet;
    }
}
