package com.creative.mahir_floral_management.view.fragment;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.mahir_floral_management.R;
import com.android.volley.VolleyError;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.appdata.remote.ApiObserver;
import com.creative.mahir_floral_management.databinding.FragmentTimeSheetBinding;
import com.creative.mahir_floral_management.model.TimeSheetInfo;
import com.creative.mahir_floral_management.viewmodel.TimeSheetFragViewModel;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeSheetFragment extends BaseFragment {

    private FragmentTimeSheetBinding fragmentTimeSheetBinding;
    private TimeSheetFragViewModel timeSheetFragViewModel;

    private  int ignoreFirstTwoWeekChangeCounter = 0;
    private  int ignoreFirstTwoYearChangeCounter = 0;
    private static int cacheLastWeekValue = 0;
    private static int cacheLastYearValue = 0;

    private int selectedWeek = 0;
    private int selectedYear = 0;

    Spinner sp_week;


    public TimeSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(false);
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


        if(savedInstanceState != null){
            selectedYear = savedInstanceState.getInt("year");
            selectedWeek = savedInstanceState.getInt("week");

        }else{
            selectedWeek = CommonMethods.getCurrentWeekNumber();
            selectedYear = CommonMethods.getCurrentYear();
        }




        getRemoteUserTimeSheets(selectedWeek, selectedYear);

        updateDropDownListViewSelection(selectedWeek, selectedYear);
        updateUI();

        observeOnWeekDropDownChange();
        observeOnYearDropDownChange();
        observerUserClickEvent();
    }

    private void getRemoteUserTimeSheets(final int cur_week, final int cur_year) {

       // Log.d("DEBUG_A", String.valueOf(cur_week) +" "+ String.valueOf(cur_year));

        showProgressDialog("Loading...", true, false);

        //timeSheetFragViewModel.getRemoteUserTimeSheets(cur_week, cur_year).removeObservers(this);
        timeSheetFragViewModel.getRemoteUserTimeSheets(cur_week, cur_year).observe(this, new ApiObserver<>(new ApiObserver.ChangeListener<HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>>>() {


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

        fragmentTimeSheetBinding.spWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(selectedWeek -1 == i) return;

                selectedWeek = i+1;

                Log.d("DEBUG", "its in spWeek " + i);


                //String[] stringArray = getResources().getStringArray(R.array.year);
               // String year = stringArray[sele];

                updateUI();
                getRemoteUserTimeSheets(i +1, selectedYear);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void observeOnYearDropDownChange() {


        fragmentTimeSheetBinding.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] stringArray = getResources().getStringArray(R.array.year);
                String year = stringArray[i];

                if(Integer.parseInt(year) == selectedYear)return;

                selectedYear = Integer.parseInt(year);

               // Log.d("DEBUG", "its in year " + i);
                updateUI();
                getRemoteUserTimeSheets(selectedWeek, Integer.parseInt(year));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void observerUserClickEvent(){
        timeSheetFragViewModel.getUserButtonClickEvent().observe(this, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {



                int id = view.getId();
                if(id == fragmentTimeSheetBinding.btnNextWeek.getId()){
                    if(selectedWeek > 30){
                        Toast.makeText(getActivity(), "Exceed week number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    fragmentTimeSheetBinding.spWeek.setSelection(selectedWeek );

                  //  getRemoteUserTimeSheets(selectedWeek + 1, selectedYear);

                }else if(id == fragmentTimeSheetBinding.btnPrevWeek.getId()){
                    if(selectedWeek < 2){
                        Toast.makeText(getActivity(), "Exceed week number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    fragmentTimeSheetBinding.spWeek.setSelection(selectedWeek - 2 );
                }
            }
        });

    }


    private void updateDropDownListViewSelection(int cur_week, int cur_year) {


        String[] stringArray = getResources().getStringArray(R.array.year);
        int yearPosition = Arrays.asList(stringArray).indexOf(String.valueOf(cur_year));

        fragmentTimeSheetBinding.spWeek.setSelection(cur_week - 1 );
        fragmentTimeSheetBinding.spYear.setSelection(yearPosition);


    }

    private void updateUI(){
        timeSheetFragViewModel.setYear(String.valueOf(selectedYear));
        timeSheetFragViewModel.setMonth(CommonMethods.getMonthNameFromWeekNum(selectedWeek));
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("week",selectedWeek);
        savedInstanceState.putInt("year",selectedYear);
    }


    private void populateTimeSheetTable(HashMap<String, HashMap<String, TimeSheetInfo.TimeSheet>> mapUserTimeSheet, int week, int year) {

        String days[] = CommonMethods.getDaysOfSpecificWeek(week, year, "dd MMM yyyy");


        fragmentTimeSheetBinding.tbTimeSheet.removeAllViews();

        if (mapUserTimeSheet == null || mapUserTimeSheet.isEmpty()) {
            fragmentTimeSheetBinding.tvAlertNoData.setVisibility(View.VISIBLE);
            return;
        }
        fragmentTimeSheetBinding.tvAlertNoData.setVisibility(View.GONE);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // code for portrait mode
        } else {
            // code for landscape mode
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
                    tv.setText("--");
                }
                tableRow.addView(tv);
            }

            fragmentTimeSheetBinding.tbTimeSheet.addView(tableRow);
        }

    }


}
