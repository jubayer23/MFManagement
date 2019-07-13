package com.creative.mahir_floral_management.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.creative.mahir_floral_management.appdata.remote.RawStockAPI;
import com.creative.mahir_floral_management.model.BaseModel;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RawStockEntryViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private RawStockAPI rawStockAPI = new RawStockAPI();

    private String[] unitArray;

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> resultLiveData = new MutableLiveData<>();

    public MutableLiveData<String> productName = new MutableLiveData<>();
    public MutableLiveData<Integer> quantity = new MutableLiveData<>();
    public MutableLiveData<Integer> unit = new MutableLiveData<>();
    public MutableLiveData<String> color = new MutableLiveData<>();
    public MutableLiveData<String> comment = new MutableLiveData<>();

    public void setUnitArray(String[] stringArray) {
        this.unitArray = stringArray;
    }

    public void afterNameChange(CharSequence s) {
        productName.setValue(s.toString());
    }

    public void afterQuantityChange(CharSequence s) {

        if (!TextUtils.isEmpty(s.toString()))
            quantity.setValue(Integer.parseInt(s.toString()));
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

    public void afterColorChange(CharSequence s) {
        color.setValue(s.toString());
    }

    public void afterCommentChange(CharSequence s) {
        comment.setValue(s.toString());
    }

    public void onClick(View view) {
        saveRawStocks();
    }

    private boolean validationChecked() {

        if (TextUtils.isEmpty(productName.getValue())) {
            validationLiveData.postValue("Please select Product name");
            return false;
        }

        if (null == quantity.getValue() || quantity.getValue() == 0) {
            validationLiveData.postValue("Please select Quantity");
            return false;
        }

        if (null == unit.getValue() || unit.getValue() == 0) {
            validationLiveData.postValue("Please select Unit");
            return false;
        }

        if (TextUtils.isEmpty(color.getValue())) {
            validationLiveData.postValue("Please select Color");
            return false;
        }

        if (TextUtils.isEmpty(comment.getValue())) {
            validationLiveData.postValue("Please enter Comment");
            return false;
        }

        return true;

    }

    private void saveRawStocks() {
        if (validationChecked()) {

            loadingLiveData.postValue(true);
            rawStockAPI.saveRawStock(
                    productName.getValue(), quantity.getValue(),
                    getSelectedUnit(), color.getValue(), comment.getValue(),
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
                    }
            );

        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

}