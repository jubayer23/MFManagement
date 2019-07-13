package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.DemandStockAPI;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ReadyStock;

import java.util.Arrays;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MakeDemandViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private DemandStockAPI demandStockAPI = new DemandStockAPI();

    private ReadyStock stockData;
    private int selectedShopId = 0;

    public MutableLiveData<String> quantity = new MutableLiveData<>();
    public MutableLiveData<String> comment = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedPriority = new MutableLiveData<>();

    public MutableLiveData<Integer> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> resultLiveData = new MutableLiveData<>();

    private String[] priorityArray = {"High", "Medium", "Low"};

    public int getSelectedPriority() {
        if (selectedPriority.getValue() == null) return 0;
        return selectedPriority.getValue();
    }

    public void setSelectedPriority(int value) {
        // Log.d("DEBUG", "its called month");
        selectedPriority.setValue(value);
    }

    public void setStock(ReadyStock stock) {
        stockData = stock;
    }

    public void setShopId(int shopId) {
        this.selectedShopId = shopId;
    }

    private int getShopId(){

        return selectedShopId;
    }

    public int getProductQuantityDelivered() {
        return Integer.parseInt(quantity.getValue());
    }

    public void setPriorityStringArray(String[] stringArray) {
        this.priorityArray = stringArray;
    }

    private String getPriority() {
        return priorityArray[getSelectedPriority()];
    }

    private int getPriorityPosition(String priority) {

        return Arrays.asList(priorityArray).indexOf(String.valueOf(priority));

    }

    private boolean fieldsValid() {

        if (null == quantity.getValue() ||
                TextUtils.isEmpty(quantity.getValue()) ||
                Integer.parseInt(quantity.getValue()) == 0) {

            validationLiveData.postValue(1);
            return false;
        }

        if (Integer.parseInt(quantity.getValue()) > Integer.parseInt(stockData.getQuantity())) {
            validationLiveData.postValue(3);
            return false;
        }

        if (0 == selectedShopId) {
            validationLiveData.postValue(2);
            return false;
        }

        if (TextUtils.isEmpty(comment.getValue())) {
            validationLiveData.postValue(4);
            return false;
        }

        return true;
    }

    public void onClick(View view) {

        if (fieldsValid())
            makeDemand();

    }

    private void makeDemand() {

        loadingLiveData.postValue(true);
        demandStockAPI.makeDemand(
                stockData.getId(),
                Integer.parseInt(quantity.getValue()),
                getPriority(),
                getShopId(),
                new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseModel result) {

                        loadingLiveData.postValue(false);
                        resultLiveData.postValue(result);

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

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
