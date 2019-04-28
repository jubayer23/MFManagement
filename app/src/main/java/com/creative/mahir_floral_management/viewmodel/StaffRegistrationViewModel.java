package com.creative.mahir_floral_management.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.remote.ReadyStockAPI;
import com.creative.mahir_floral_management.appdata.remote.StaffRegistrationAPI;
import com.creative.mahir_floral_management.model.BaseModel;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class StaffRegistrationViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private StaffRegistrationAPI API = new StaffRegistrationAPI();

    private String[] unitArray;

    public MutableLiveData<String> validationLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public MutableLiveData<BaseModel> resultLiveData = new MutableLiveData<>();

    public MutableLiveData<String> staffName = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedShop = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedRole = new MutableLiveData<>();



    public int getSelectedShop() {
        if (selectedShop.getValue() == null) return 0;
        return selectedShop.getValue();
    }

    public void setSelectedShop(int value) {
        // Log.d("DEBUG", "its called month");
        selectedShop.setValue(value);

    }


    public int getSelectedRole() {
        if (selectedRole.getValue() == null) return 0;
        return selectedRole.getValue();
    }

    public void setSelectedRole(int value) {
        // Log.d("DEBUG", "its called month");
        selectedRole.setValue(value);

    }


    public void onClick(View view) {
        staffRegistration();
    }

    private boolean validationChecked() {

        if (TextUtils.isEmpty(staffName.getValue())) {
            validationLiveData.postValue("Staff name missing");
            return false;
        }

        if (TextUtils.isEmpty(email.getValue())) {
            validationLiveData.postValue("Email address missing");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.getValue()).matches()) {
            validationLiveData.postValue("Invalid Email Address");
            return false;
        }

        if (TextUtils.isEmpty(password.getValue())) {
            validationLiveData.postValue("Password missing");
            return false;
        }

        if (null == selectedShop.getValue() || selectedShop.getValue() == 0) {
            validationLiveData.postValue("Please select shop");
            return false;
        }

        if (null == selectedRole.getValue() || selectedRole.getValue() == 0) {
            validationLiveData.postValue("Please select Role");
            return false;
        }

        return true;

    }

    private void staffRegistration() {
        if (validationChecked()) {

            loadingLiveData.postValue(true);
            API.staffRegistration(
                    staffName.getValue(),
                    email.getValue(),
                    password.getValue(),
                    selectedShop.getValue(),
                    GlobalAppAccess.role_set_in_server[selectedRole.getValue()],
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
