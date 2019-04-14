package com.creative.mahir_floral_management.appdata.remote;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.model.TimeSheetInfo;
import com.creative.mahir_floral_management.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSheetApi {

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


        final StringRequest req = new StringRequest(Request.Method.POST, APIUrl.URL_USER_TIMESHEET,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG",response);

                       /* try {
                            JSONObject jsonObject = new JSONObject(response);


                            boolean status = jsonObject.getBoolean("status");
                            dataWrapper.setErrorMessage(jsonObject.getString("message"));

                            if(status){
                                UserInfo userInfo = MydApplication.gson.fromJson(String.valueOf(jsonObject.getJSONObject("user_info")), UserInfo.class);
                                dataWrapper.setData(userInfo);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/



                        TimeSheetInfo timeSheetInfo = MydApplication.gson.fromJson(response, TimeSheetInfo.class);
                        HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> map;
                        if(timeSheetInfo.getStatus()){
                            map = parseData(timeSheetInfo);
                        }else{
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



    private  HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> parseData(TimeSheetInfo timeSheetInfo){

        HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> mapUserTimeSheet = new HashMap<>();
        mapUserTimeSheet.clear();

        if(timeSheetInfo.getUsers() == null || timeSheetInfo.getUsers().isEmpty()){

            return  mapUserTimeSheet;

        }

        List<TimeSheetInfo.User> users = timeSheetInfo.getUsers();


        for( TimeSheetInfo.User user: users){
            String name = user.getUserName();

            HashMap<String, TimeSheetInfo.TimeSheet> mapTimeSheet = new HashMap<>();

            for(TimeSheetInfo.TimeSheet timeSheet: user.getTimeSheets()){
                String date = timeSheet.getDate();
                String chengeDateFormat = CommonMethods.changeFormat(date,"dd/MM/yyyy", "dd MMM yyyy");


                mapTimeSheet.put(chengeDateFormat, timeSheet);

            }

            mapUserTimeSheet.put(name, mapTimeSheet);
        }

        return mapUserTimeSheet;
    }
}
