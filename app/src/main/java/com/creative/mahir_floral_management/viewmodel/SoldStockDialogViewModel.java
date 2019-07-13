package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.ShopStockAPI;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ShopStock;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SoldStockDialogViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private ShopStockAPI shopStockAPI = new ShopStockAPI();

    private ShopStock shopStock;

    public MutableLiveData<String> productAmount = new MutableLiveData<>();
    public MutableLiveData<String> comment = new MutableLiveData<>();

    public MutableLiveData<Integer> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> resultLiveData = new MutableLiveData<>();

    public void setShopStock(ShopStock stock) {
        shopStock = stock;
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

        if (Integer.parseInt(productAmount.getValue()) > Integer.parseInt(shopStock.getQuantity())) {
            validationLiveData.postValue(3);
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
            soldItem();

    }

    private void soldItem() {
        loadingLiveData.postValue(true);
        shopStockAPI.soldShopStock(
                shopStock.getId(),
                Integer.parseInt(productAmount.getValue()),
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
