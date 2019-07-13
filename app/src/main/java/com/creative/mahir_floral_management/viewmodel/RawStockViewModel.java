package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.remote.RawStockAPI;
import com.creative.mahir_floral_management.model.RawStock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public MutableLiveData<CharSequence> searchText = new MutableLiveData<>();

    private int last_selected_month = 0;
    private int last_selected_year = 0;
    private boolean isFirstLoad = true;

    public RawStockViewModel() {

        //Load current month and year
        int year = CommonMethods.getCurrentYear();
        int month = CommonMethods.getCurrentMonth();


        last_selected_month = month + 1;
        last_selected_year = getYearPosition(year);

        selectedMonth.setValue(last_selected_month);
        selectedYear.setValue(last_selected_year);

        if (isFirstLoad) {
            isFirstLoad = false;
            getRawStocks();
        }


    }

    public int getSelectedMonth() {
        if (selectedMonth.getValue() == null) return 0;
        return selectedMonth.getValue();
    }

    public void setSelectedMonth(int value) {
        // Log.d("DEBUG", "its called month");
        selectedMonth.setValue(value);
        if (last_selected_month != value) {
            last_selected_month = value;
            getRawStocks();
        }

    }

    public int getSelectedYear() {
        if (selectedYear.getValue() == null) return 0;
        return selectedYear.getValue();
    }

    public void setSelectedYear(int value) {
        //Log.d("DEBUG", "its called year");
        selectedYear.setValue(value);
        if (last_selected_year != value) {
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
                            Collections.sort(records, new RawStock.timeComparatorDesc());

                            mutableLiveData.postValue(records);
                            loadingLiveData.postValue(false);

                        }

                        @Override
                        public void onError(Throwable e) {

                            Log.e("", "", e);
                            validationLiveData.postValue(e.getMessage());
                            loadingLiveData.postValue(false);

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }


    public void requestToRefresh() {

        //Load current month and year
        int year = CommonMethods.getCurrentYear();
        int month = CommonMethods.getCurrentMonth();

        if (selectedMonth.getValue() == (month + 1) && Integer.parseInt(getYear()) == year)
            getRawStocks();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
