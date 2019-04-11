package com.creative.mahir_floral_management.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.appdata.remote.ApiObserver;
import com.creative.mahir_floral_management.databinding.FragmentTimeSheetBinding;
import com.creative.mahir_floral_management.model.TimeSheetInfo;
import com.creative.mahir_floral_management.viewmodel.TimeSheetFragViewModel;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeSheetFragment extends BaseFragment {

    private FragmentTimeSheetBinding fragmentTimeSheetBinding;
    private TimeSheetFragViewModel timeSheetFragViewModel;


    public TimeSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        timeSheetFragViewModel = ViewModelProviders.of(this).get(TimeSheetFragViewModel.class);

        fragmentTimeSheetBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_time_sheet,container,false);
        fragmentTimeSheetBinding.setLifecycleOwner(this);
        fragmentTimeSheetBinding.setTimeSheetFragViewModel(timeSheetFragViewModel);

        View view = fragmentTimeSheetBinding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //observeOnWeekDropDownChange();
        //observeOnYearDropDownChange();


        int cur_week  = CommonMethods.getCurrentWeekNumber();
        int cur_year = CommonMethods.getCurrentYear();


        getRemoteUserTimeSheets(cur_week, cur_year);

        updateUIBasedOnCurrentDate(cur_week,cur_year);



    }

    private void getRemoteUserTimeSheets(final int cur_week, final int cur_year){

        timeSheetFragViewModel.getRemoteUserTimeSheets(cur_week, cur_year).observe(this, new ApiObserver<TimeSheetInfo>(new ApiObserver.ChangeListener<TimeSheetInfo>() {
            @Override
            public void onSuccess(TimeSheetInfo timeSheetInfo) {
                if(timeSheetInfo.getStatus()){
                    if(timeSheetInfo.getUsers() != null && !timeSheetInfo.getUsers().isEmpty()){


                        parseData(timeSheetInfo.getUsers());

                        populateTimeSheetTable(cur_week,cur_year);
                    }else{
                        //TODO show no table data
                    }
                }
            }

            @Override
            public void onException(VolleyError volleyError) {

            }
        }));
    }


    private void observeOnWeekDropDownChange(){
        timeSheetFragViewModel.selectedWeek.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

            }
        });
    }

    private void observeOnYearDropDownChange(){
        timeSheetFragViewModel.selectedYear.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                String[] years = getResources().getStringArray(R.array.year);
            }
        });
    }


    private void updateUIBasedOnCurrentDate(int cur_week, int cur_year){



        String[] stringArray = getResources().getStringArray(R.array.year);
        int yearPosition =  Arrays.asList(stringArray).indexOf(String.valueOf(cur_year));

        timeSheetFragViewModel.setSelectedWeek(cur_week);
        timeSheetFragViewModel.setSelectedYear(yearPosition);

       // populateTableData();

    }


    private void populateTimeSheetTable(int week, int year){

        String days[]  = CommonMethods.getDaysOfSpecificWeek(week, year, "dd MMM yyyy");



        fragmentTimeSheetBinding.tbTimeSheet.removeAllViews();




        for( int row =0 ; row < 2; row++){

            TableRow tableRow = new TableRow(getActivity());

            for(int colom = 0 ; colom < 9; colom++){
                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                int padding = MydApplication.getInstance().getPixelValue(10);
                tv.setPadding(padding,padding,padding,padding);

                tv.setTextColor(Color.WHITE);
                tv.setText("Test Test");

                if(row == 0){
                    if(colom == 0){
                        tv.setBackgroundResource(R.drawable.table_cell_shape);

                        tv.setText("Week "+ week);
                    }else if(colom >0 && colom < 8){
                        tv.setText(days[ colom - 1]);
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border);
                    }else{
                        tv.setText("");
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border);
                    }
                }else{
                    if(colom == 0){
                        tv.setBackgroundResource(R.drawable.table_cell_shape);
                        tv.setText("Staff");
                    }else if(colom >0 && colom < 8){
                        tv.setText(GlobalAppAccess.week_day_name[ colom - 1]);
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border);
                    }else{
                        tv.setText("T. Hours");
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border);
                    }
                }




                tableRow.addView(tv);
            }

            fragmentTimeSheetBinding.tbTimeSheet.addView(tableRow);
        }

    }


    HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> mapUserTimeSheet = new HashMap<>();

    private void parseData(List<TimeSheetInfo.User> users){

        mapUserTimeSheet.clear();

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
    }
}
