package com.creative.mahir_floral_management.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.appdata.remote.RawStockAPI;
import com.creative.mahir_floral_management.appdata.remote.ReturnStockApi;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.RawStock;
import com.creative.mahir_floral_management.model.ReturnStock;
import com.creative.mahir_floral_management.model.ShopStock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ReturnStockViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private ReturnStockApi returnStockApi = new ReturnStockApi();

    private List<ReturnStock> records = new ArrayList<>(0);
    public MutableLiveData<List<ReturnStock>> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> markReceivedLiveData = new MutableLiveData<>();

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    private String[] stringArray = {"2019", "2020", "2021"};

    public MutableLiveData<Integer> selectedMonth = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedYear = new MutableLiveData<>();
    public MutableLiveData<CharSequence> searchText = new MutableLiveData<>();

    private int last_selected_month = 0;
    private int last_selected_year = 0;
    private boolean isFirstLoad = true;

    public ReturnStockViewModel() {

        //Load current month and year
        int year = CommonMethods.getCurrentYear();
        int month = CommonMethods.getCurrentMonth();


        last_selected_month = month + 1;
        last_selected_year = getYearPosition(year);

        selectedMonth.setValue(last_selected_month);
        selectedYear.setValue(last_selected_year);

        if (isFirstLoad) {
            isFirstLoad = false;
            getReturnStocks();
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
            getReturnStocks();
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
            getReturnStocks();
        }

    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }



    private String getYear() {
        return stringArray[getSelectedYear()];
    }

    private boolean validationChecked() {

        if (getSelectedMonth() == 0) {
            validationLiveData.postValue("Please select month");
            return false;
        }

        return true;

    }



    private void getReturnStocks() {
        if (validationChecked()) {

            loadingLiveData.postValue(true);

            returnStockApi.getReturnStock(getSelectedMonth(), getYear(), "",
                    new Observer<List<ReturnStock>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(List<ReturnStock> stockList) {

                            records.clear();
                            records.addAll(stockList);
                            Collections.sort(records, new ReturnStock.timeComparatorDesc());

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

    public void markReceiveReturnStock(final ReturnStock item) {

        loadingLiveData.postValue(true);
        returnStockApi.markReturnStockReceived(
                item.getId(),
                item.getShopName(),
                new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {

                        loadingLiveData.postValue(false);
                        markReceivedLiveData.postValue(baseModel);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("", "", e);
                        loadingLiveData.postValue(false);

                        BaseModel baseModel = new BaseModel();
                        baseModel.setStatus(false);
                        baseModel.setMessage(e.getMessage());

                        markReceivedLiveData.postValue(baseModel);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

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
}
