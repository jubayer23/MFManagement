package com.creative.mahir_floral_management.viewmodel;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.creative.mahir_floral_management.appdata.remote.ReadyStockAPI;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ReadyStock;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ReadyStockDialogUpdateViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private ReadyStockAPI readyStockAPI = new ReadyStockAPI();

    private ReadyStock stockData;

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> resultLiveData = new MutableLiveData<>();

    public MutableLiveData<String> productQuantity = new MutableLiveData<>();
    public MutableLiveData<String> productName = new MutableLiveData<>();
    public MutableLiveData<String> productPrice = new MutableLiveData<>();
    public MutableLiveData<Integer> unit = new MutableLiveData<>();
    public MutableLiveData<String> productColor = new MutableLiveData<>();

    private String[] unitArray;

    public void setUnitArray(String[] stringArray) {
        this.unitArray = stringArray;
    }

    public void setStock(ReadyStock stock) {
        stockData = stock;
    }

    public ReadyStock getStock(){
        return stockData;
    }

    public int getUnit() {
        if (unit.getValue() == null) return 0;
        return unit.getValue();
    }

    public void setUnit(int value) {
        unit.setValue(value);
    }

    private String getSelectedUnit() {
        return unitArray[getUnit()];
    }

    public void onUpdateButtonClick(View view) {

        if (isValid())
            updateStock();

    }

    private void updateStock() {

        loadingLiveData.postValue(true);
        readyStockAPI.updateReadyStock(
                stockData.getId(),
                productName.getValue(),
                productQuantity.getValue(),
                productPrice.getValue(),
                getSelectedUnit(),
                productColor.getValue(),
                new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseModel result) {

                        stockData.setName(productName.getValue());
                        stockData.setQuantity(productQuantity.getValue());
                        stockData.setPrice(productPrice.getValue());
                        stockData.setUnit(getSelectedUnit());
                        stockData.setColor(productColor.getValue());

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






    private boolean isValid() {

        if (null == productQuantity.getValue() ||
                TextUtils.isEmpty(productQuantity.getValue()) ||
                Integer.parseInt(productQuantity.getValue()) == 0) {

            validationLiveData.postValue("Required");
            return false;
        }

        if (null == productPrice.getValue() || TextUtils.isEmpty(productPrice.getValue()) ||
                Float.parseFloat(productPrice.getValue()) == 0) {
            validationLiveData.postValue("Required");
            return false;
        }


        if (null == unit.getValue() || unit.getValue() == 0) {
            validationLiveData.postValue("Please select Unit");
            return false;
        }

        if (TextUtils.isEmpty(productColor.getValue())) {
            validationLiveData.postValue("Required");
            return false;
        }



        return true;
    }


}
