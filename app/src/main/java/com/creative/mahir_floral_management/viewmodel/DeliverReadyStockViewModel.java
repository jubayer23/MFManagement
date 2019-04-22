package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.RawStockAPI;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.RawStock;
import com.creative.mahir_floral_management.model.ReadyStock;
import com.creative.mahir_floral_management.model.ShopInfo;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DeliverReadyStockViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private RawStockAPI rawStockAPI = new RawStockAPI();

    private ReadyStock stockData;
    private ShopInfo.Shop selectedShop;

    public MutableLiveData<String> productAmount = new MutableLiveData<>();
    public MutableLiveData<String> comment = new MutableLiveData<>();

    public MutableLiveData<Integer> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> resultLiveData = new MutableLiveData<>();

    public void setStock(ReadyStock stock) {
        stockData = stock;
    }

    public void setSelectedShop(ShopInfo.Shop selectedShop) {
        this.selectedShop = selectedShop;
    }

    public void afterAmountChange(CharSequence s) {
        productAmount.setValue(s.toString());
    }

    public void afterCommentChange(CharSequence s) {
        comment.setValue(s.toString());
    }

    public int getProductQuantityDelivered() {
        return Integer.parseInt(productAmount.getValue());
    }

    private boolean fieldsValid() {

        if (null == productAmount.getValue() ||
                TextUtils.isEmpty(productAmount.getValue()) ||
                Integer.parseInt(productAmount.getValue()) == 0) {

            validationLiveData.postValue(1);
            return false;
        }

        if (Integer.parseInt(productAmount.getValue()) > Integer.parseInt(stockData.getQuantity())) {
            validationLiveData.postValue(3);
            return false;
        }

        if (null == selectedShop) {
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
            sendDelivery();

    }

    private void sendDelivery() {

        loadingLiveData.postValue(true);
        rawStockAPI.deliverReadyStock(
                stockData.getId(),
                Integer.parseInt(productAmount.getValue()),
                selectedShop.getId(),
                comment.getValue(),
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

                        BaseModel baseModel = new BaseModel();
                        baseModel.setStatus(false);
                        baseModel.setMessage(e.getMessage());

                        resultLiveData.postValue(baseModel);

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
