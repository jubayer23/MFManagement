package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.creative.mahir_floral_management.appdata.remote.RawStockAPI;
import com.creative.mahir_floral_management.model.RawStock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RawStockViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private RawStockAPI rawStockAPI = new RawStockAPI();

    private List<RawStock> records = new ArrayList<>(0);
    public MutableLiveData<List<RawStock>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    private String[] stringArray = {"2019", "2020", "2021"};

    public MutableLiveData<Integer> selectedMonth = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedYear = new MutableLiveData<>();

    private int last_selected_month = 0;
    private int last_selected_year = 0;

    private boolean isFirstLoad = true;

    public RawStockViewModel() {

        //Load current month and year
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);



        last_selected_month = month + 1;
        last_selected_year = getYearPosition(year);

        selectedMonth.setValue(last_selected_month);
        selectedYear.setValue(last_selected_year);

        if(isFirstLoad){
            isFirstLoad = false;
            getRawStocks();
        }


    }

    public int getSelectedMonth() {
        if (selectedMonth.getValue() == null) return 0;
        return selectedMonth.getValue();
    }

    public void setSelectedMonth(int value) {
        Log.d("DEBUG", "its called month");
        selectedMonth.setValue(value);
        if(last_selected_month != value){
            last_selected_month = value;
            getRawStocks();
        }

    }

    public int getSelectedYear() {
        if (selectedYear.getValue() == null) return 0;
        return selectedYear.getValue();
    }

    public void setSelectedYear(int value) {
        Log.d("DEBUG", "its called year");
        selectedYear.setValue(value);
        if( last_selected_year != value){
            last_selected_year = value;
            getRawStocks();
        }

    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    private String getYear() {
        return stringArray[getSelectedYear()];
    }

    private int getYearPosition(int year) {

        int index = 0;
        final String currentYear = String.valueOf(year);
        for (String yearStr : stringArray) {

            if (currentYear.equalsIgnoreCase(yearStr))
                return index;

            index++;

        }

        return 0;
    }

    public void afterUserNameChange(CharSequence s) {
        searchList(s.toString().toLowerCase());
    }

    private boolean validationChecked() {

        if (getSelectedMonth() == 0) {
            validationLiveData.postValue("Please select month");
            return false;
        }

        return true;

    }

    private void getRawStocks() {
        if (validationChecked()) {

            loadingLiveData.postValue(true);

            rawStockAPI.getRawStock(getSelectedMonth(), getYear(),
                    new Observer<List<RawStock>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(List<RawStock> stockList) {

                            records.clear();
                            records.addAll(stockList);
                            sortList();

                            mutableLiveData.postValue(records);
                            loadingLiveData.postValue(false);

                        }

                        @Override
                        public void onError(Throwable e) {

                            Log.e("", "", e);
                            loadingLiveData.postValue(false);

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }

    private void searchList(String searchTxt) {

        if (records.size() == 0) return;

        if (TextUtils.isEmpty(searchTxt)) {

            mutableLiveData.postValue(records);

            return;
        }

        //Search for name
        final List<RawStock> searchRecords = new ArrayList<>(0);
        String stockName;

        for (RawStock rawStock : records) {

            stockName = rawStock.getName().toLowerCase();

            if (stockName.equalsIgnoreCase(searchTxt) || stockName.contains(searchTxt))
                searchRecords.add(rawStock);

        }

        mutableLiveData.postValue(searchRecords);

    }

    public void requestToRefresh() {

        //Load current month and year
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        if (selectedMonth.getValue() == (month + 1) && Integer.parseInt(getYear()) == year)
            getRawStocks();

    }

    private void sortList()  {

        Collections.sort(records, new Comparator<RawStock>() {

            DateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            @Override
            public int compare(RawStock o1, RawStock o2) {
                try {
                    return f.parse(o2.getReceived_date()).compareTo(f.parse(o1.getReceived_date()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
