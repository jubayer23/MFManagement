package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.creative.mahir_floral_management.appdata.remote.DemandStockAPI;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.DemandedStock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DemandedStocksViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private CompositeDisposable disposable = new CompositeDisposable();
    private DemandStockAPI demandStockAPI = new DemandStockAPI();

    private List<DemandedStock> records = new ArrayList<>(0);
    public MutableLiveData<List<DemandedStock>> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> markCompletedLiveData = new MutableLiveData<>();

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    private String[] stringArray = {"shop 01", "shop 02", "shop 03"};

    public MutableLiveData<Integer> selectedShop = new MutableLiveData<>();
    public MutableLiveData<CharSequence> searchText = new MutableLiveData<>();

    private int last_selected_shop = 0;
    private boolean isFirstLoad = true;

    public DemandedStocksViewModel() {

        //Load current month and year



        last_selected_shop = 0;

        selectedShop.setValue(last_selected_shop);

        if (isFirstLoad) {
            isFirstLoad = false;
            getDemandedStocks();
        }

    }


    public int getSelectedShop() {
        if (selectedShop.getValue() == null) return 0;
        return selectedShop.getValue();
    }

    public void setSelectedShop(int value) {
         Log.d("DEBUG", "its called month " + value);
        selectedShop.setValue(value);
        if (last_selected_shop != value) {
            last_selected_shop = value;
            getDemandedStocks();
        }

    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    private boolean validationChecked() {

        if (getSelectedShop() < 0) {
            validationLiveData.postValue("Please select month");
            return false;
        }

        return true;

    }

    private void getDemandedStocks() {
        if (validationChecked()) {

            loadingLiveData.postValue(true);

            demandStockAPI.getDemandedStocks(getSelectedShop(),
                    new Observer<List<DemandedStock>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(List<DemandedStock> stockList) {

                            records.clear();
                            records.addAll(stockList);
                            Collections.sort(records, new DemandedStock.timeComparatorDesc());

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


    public void markReceive(final DemandedStock item) {

        loadingLiveData.postValue(true);
        demandStockAPI.completeDemand(
                item.getId(),
                new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {

                        loadingLiveData.postValue(false);
                        markCompletedLiveData.postValue(baseModel);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("", "", e);
                        loadingLiveData.postValue(false);

                        BaseModel baseModel = new BaseModel();
                        baseModel.setStatus(false);
                        baseModel.setMessage(e.getMessage());

                        markCompletedLiveData.postValue(baseModel);

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
