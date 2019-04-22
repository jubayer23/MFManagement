package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.creative.mahir_floral_management.appdata.remote.RawStockAPI;
import com.creative.mahir_floral_management.appdata.remote.ReadyStockAPI;
import com.creative.mahir_floral_management.model.RawStock;
import com.creative.mahir_floral_management.model.ReadyStock;

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

public class ReadyStockViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private ReadyStockAPI readyStockAPI = new ReadyStockAPI();

    private List<ReadyStock> records = new ArrayList<>(0);
    public MutableLiveData<List<ReadyStock>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    private String[] stringArray = {"2019", "2020", "2021"};

    public MutableLiveData<Integer> selectedMonth = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedYear = new MutableLiveData<>();

    public ReadyStockViewModel() {

        //Load current month and year
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        selectedMonth.setValue(month + 1);
        selectedYear.setValue(getYearPosition(year));

    }

    public int getSelectedMonth() {
        if (selectedMonth.getValue() == null) return 0;
        return selectedMonth.getValue();
    }

    public void setSelectedMonth(int value) {
        selectedMonth.setValue(value);
        getReadyStocks();
    }

    public int getSelectedYear() {
        if (selectedYear.getValue() == null) return 0;
        return selectedYear.getValue();
    }

    public void setSelectedYear(int value) {
        selectedYear.setValue(value);
        getReadyStocks();
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

    private void getReadyStocks() {
        if (validationChecked()) {

            loadingLiveData.postValue(true);

            readyStockAPI.getReadyStock(getSelectedMonth(), getYear(),
                    new Observer<List<ReadyStock>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(List<ReadyStock> stockList) {

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
        final List<ReadyStock> searchRecords = new ArrayList<>(0);
        String stockName;

        for (ReadyStock readyStock : records) {

            stockName = readyStock.getName().toLowerCase();

            if (stockName.equalsIgnoreCase(searchTxt) || stockName.contains(searchTxt))
                searchRecords.add(readyStock);

        }

        mutableLiveData.postValue(searchRecords);

    }

    public void requestToRefresh() {

        //Load current month and year
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        if (selectedMonth.getValue() == (month + 1) && Integer.parseInt(getYear()) == year)
            getReadyStocks();

    }

    private void sortList() {

        Collections.sort(records, new Comparator<ReadyStock>() {

            DateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            @Override
            public int compare(ReadyStock o1, ReadyStock o2) {
                try {
                    return f.parse(o2.getReceivedDate()).compareTo(f.parse(o1.getReceivedDate()));
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
