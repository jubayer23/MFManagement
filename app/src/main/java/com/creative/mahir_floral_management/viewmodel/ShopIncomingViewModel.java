package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.creative.mahir_floral_management.appdata.remote.ShopStockAPI;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ShopStock;
import com.creative.mahir_floral_management.model.SoldStock;

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

public class ShopIncomingViewModel extends ViewModel {

    private int shopID;

    private CompositeDisposable disposable = new CompositeDisposable();
    private ShopStockAPI shopStockAPI = new ShopStockAPI();

    private List<ShopStock> records = new ArrayList<>(0);
    public MutableLiveData<List<ShopStock>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> markReceivedLiveData = new MutableLiveData<>();

    private String[] stringArray = {"2019", "2020", "2021"};

    public MutableLiveData<Integer> selectedMonth = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedYear = new MutableLiveData<>();
    public MutableLiveData<CharSequence> searchText = new MutableLiveData<>();

    public ShopIncomingViewModel() {

        //Load current month and year
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        selectedMonth.setValue(month + 1);
        selectedYear.setValue(getYearPosition(year));

    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public int getSelectedMonth() {
        if (selectedMonth.getValue() == null) return 0;
        return selectedMonth.getValue();
    }

    public void setSelectedMonth(int value) {
        selectedMonth.setValue(value);
        getShopStocks();
    }

    public int getSelectedYear() {
        if (selectedYear.getValue() == null) return 0;
        return selectedYear.getValue();
    }

    public void setSelectedYear(int value) {
        selectedYear.setValue(value);
        getShopStocks();
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

    private void getShopStocks() {
        if (validationChecked()) {

            loadingLiveData.postValue(true);
            shopStockAPI.getShopIncomingStock(
                    getSelectedMonth(), getYear(), shopID,
                    new Observer<List<ShopStock>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(List<ShopStock> stockList) {

                            records.clear();
                            records.addAll(stockList);
                            Collections.sort(records, new ShopStock.timeComparatorOnDeliveryDateDesc());

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



    public void markReceive(final ShopStock item) {

        loadingLiveData.postValue(true);
        shopStockAPI.markShopStockReceived(
                item.getId(),
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



    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
