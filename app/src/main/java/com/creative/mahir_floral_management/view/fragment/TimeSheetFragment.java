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

import com.creative.mahir_floral_management.R;
import com.android.volley.VolleyError;
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

    private static int ignoreFirstTwoWeekChangeCounter = 0;
    private static int ignoreFirstTwoYearChangeCounter = 0;


    public TimeSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        timeSheetFragViewModel = ViewModelProviders.of(this).get(TimeSheetFragViewModel.class);

        fragmentTimeSheetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_sheet, container, false);
        fragmentTimeSheetBinding.setLifecycleOwner(this);
        fragmentTimeSheetBinding.setTimeSheetFragViewModel(timeSheetFragViewModel);

        View view = fragmentTimeSheetBinding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        observeOnWeekDropDownChange();
        observeOnYearDropDownChange();


        int cur_week = CommonMethods.getCurrentWeekNumber();
        int cur_year = CommonMethods.getCurrentYear();


        getRemoteUserTimeSheets(cur_week, cur_year);

        updateUIBasedOnCurrentDate(cur_week, cur_year);


    }

    private void getRemoteUserTimeSheets(final int cur_week, final int cur_year) {

        //Log.d("DEBUG_A", String.valueOf(cur_week) +" "+ String.valueOf(cur_year));

        showProgressDialog("Loading...", true, false);

        timeSheetFragViewModel.getRemoteUserTimeSheets(cur_week, cur_year).removeObservers(this);
        timeSheetFragViewModel.getRemoteUserTimeSheets(cur_week, cur_year).observe(this, new ApiObserver<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>>(new ApiObserver.ChangeListener<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>>() {


            @Override
            public void onSuccess(HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> dataWrapper) {

                dismissProgressDialog();


                //parseData(timeSheetInfo.getUsers(), cur_week, cur_year);

                populateTimeSheetTable(dataWrapper, cur_week, cur_year);



            }

            @Override
            public void onException(VolleyError volleyError) {
                dismissProgressDialog();
            }
        }));
    }


    private void observeOnWeekDropDownChange() {
        timeSheetFragViewModel.selectedWeek.removeObservers(this);
        timeSheetFragViewModel.selectedWeek.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (ignoreFirstTwoWeekChangeCounter < 2) {
                    ignoreFirstTwoWeekChangeCounter++;
                    return;
                }

               // Log.d("DEBUG", "its called in week");


                String[] stringArray = getResources().getStringArray(R.array.year);
                String year = stringArray[timeSheetFragViewModel.getSelectedYear()];

               // Log.d("DEBUG_WEEK", String.valueOf(integer + 1));
               // Log.d("DEBUG_YEAR", " " + year);

                getRemoteUserTimeSheets(integer +1, Integer.parseInt(year));
            }
        });
    }

    private void observeOnYearDropDownChange() {
        timeSheetFragViewModel.selectedYear.removeObservers(this);
        timeSheetFragViewModel.selectedYear.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                //String[] years = getResources().getStringArray(R.array.year);
                if (ignoreFirstTwoYearChangeCounter < 2) {
                    ignoreFirstTwoYearChangeCounter++;
                    return;
                }

               // Log.d("DEBUG", "its called in year");

                 String[] stringArray = getResources().getStringArray(R.array.year);
                String year = stringArray[integer];


               // Log.d("DEBUG_YEAR", " " + year);
               // Log.d("DEBUG_WEEK",  String.valueOf(timeSheetFragViewModel.selectedWeek.getValue()+1));

                getRemoteUserTimeSheets(timeSheetFragViewModel.getSelectedWeek()+1, Integer.parseInt(year));
            }
        });
    }


    private void updateUIBasedOnCurrentDate(int cur_week, int cur_year) {


        String[] stringArray = getResources().getStringArray(R.array.year);
        int yearPosition = Arrays.asList(stringArray).indexOf(String.valueOf(cur_year));

        timeSheetFragViewModel.selectedWeek.setValue(cur_week);
         timeSheetFragViewModel.selectedYear.setValue(yearPosition);

        // populateTableData();

    }


    private void populateTimeSheetTable(HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> mapUserTimeSheet, int week, int year) {

        String days[] = CommonMethods.getDaysOfSpecificWeek(week, year, "dd MMM yyyy");


        fragmentTimeSheetBinding.tbTimeSheet.removeAllViews();

        if (mapUserTimeSheet == null || mapUserTimeSheet.isEmpty()) {
            return;
        }


        for (int row = 0; row < 2; row++) {

            TableRow tableRow = new TableRow(getActivity());

            for (int colom = 0; colom < 9; colom++) {
                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                int padding = MydApplication.getInstance().getPixelValue(10);
                tv.setPadding(padding, padding, padding, padding);

                tv.setTextColor(Color.WHITE);
                tv.setText("Test Test");

                if (row == 0) {
                    if (colom == 0) {
                        tv.setBackgroundResource(R.drawable.table_cell_shape_gray_background);

                        tv.setText("Week " + week);
                    } else if (colom > 0 && colom < 8) {
                        tv.setText(days[colom - 1]);
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border_gray);
                    } else {
                        tv.setText("");
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border_gray);
                    }
                } else {
                    if (colom == 0) {
                        tv.setBackgroundResource(R.drawable.table_cell_shape_gray_background);
                        tv.setText("Staff");
                    } else if (colom > 0 && colom < 8) {
                        tv.setText(GlobalAppAccess.week_day_name[colom - 1]);
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border_gray);
                    } else {
                        tv.setText("T. Hours");
                        tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border_gray);
                    }
                }


                tableRow.addView(tv);
            }

            fragmentTimeSheetBinding.tbTimeSheet.addView(tableRow);
        }


        for (String key : mapUserTimeSheet.keySet()) {
            // ...
            TableRow tableRow = new TableRow(getActivity());
            HashMap<String, TimeSheetInfo.TimeSheet> mapTimeSheet = mapUserTimeSheet.get(key);

            for (int colom = 0; colom < 9; colom++) {
                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                int padding = MydApplication.getInstance().getPixelValue(10);
                tv.setPadding(padding, padding, padding, padding);

                tv.setTextColor(Color.BLACK);
                tv.setText("Test Test");

                tv.setBackgroundResource(R.drawable.table_cell_shape_without_left_border_white);
                if (colom == 0) {
                    tv.setText(key);
                    tv.setBackgroundResource(R.drawable.table_cell_shape_white_background);
                } else if (colom > 0 && colom < 8) {

                    if (mapTimeSheet.get(days[colom - 1]) != null) {
                        TimeSheetInfo.TimeSheet timeSheet = mapTimeSheet.get(days[colom - 1]);
                        String in = CommonMethods.changeFormat(timeSheet.getCheckIn(), "HH:mm", "hh:mm aa");
                        String out = CommonMethods.changeFormat(timeSheet.getCheckOut(), "HH:mm", "hh:mm aa");

                        tv.setText("In: " + in + "\n" + "out: " + out);
                    } else {
                        tv.setText("No Data");
                    }

                } else {
                    tv.setText("40");
                }
                tableRow.addView(tv);
            }

            fragmentTimeSheetBinding.tbTimeSheet.addView(tableRow);
        }

    }


}
