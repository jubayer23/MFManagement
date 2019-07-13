package com.creative.mahir_floral_management.view.fragment;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.CustomSpinnerAdapter;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentStaffRegistrationBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.Shop;
import com.creative.mahir_floral_management.viewmodel.StaffRegistrationViewModel;

import java.util.ArrayList;
import java.util.List;

public class StaffRegistrationFragment extends BaseFragment {

    private AlertDialog alertDialog;

    FragmentStaffRegistrationBinding binding;

    private List<Shop> shops = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_registration, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(StaffRegistrationViewModel.class));

       // binding.getViewModel().setUnitArray(getResources().getStringArray(R.array.units));

        binding.getViewModel().validationLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showLongToast(s);
            }
        });

        binding.getViewModel().loadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {

                if (b)
                    showProgressDialog("Loading...", true, false);
                else
                    dismissProgressDialog();

            }
        });

        binding.getViewModel().resultLiveData.observe(this, new Observer<BaseModel>() {
            @Override
            public void onChanged(@Nullable BaseModel baseModel) {

                if (null == baseModel) return;
                showAlert(baseModel.getStatus(), baseModel.getMessage());

            }
        });

        setFields();


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void setFields(){
        Shop shopAll = new Shop();

        shopAll.setName("Select corresponding shop");
        shops.add(shopAll);
        shops.addAll(MydApplication.getInstance().getPrefManger().getShops());

        CustomSpinnerAdapter shopAdapter = new CustomSpinnerAdapter(getContext(), R.layout.spinner_item, R.id.tv_item,shops);

        binding.spShop.setPrompt("Select shop");
        binding.spShop.setAdapter(shopAdapter);

        //List<String> wordList = Arrays.asList(GlobalAppAccess.role_alias);


        ArrayAdapter<String> spAdapterStates = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, GlobalAppAccess.role_alias);
        binding.spRole.setPrompt("Select Role");
        binding.spRole.setAdapter(spAdapterStates);

    }

    @Override
    public void onPause() {

        if(null != alertDialog)
            alertDialog.dismiss();

        super.onPause();
    }

    private void showAlert(final boolean isSuccess, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setCancelable(false);
        builder.setTitle((isSuccess) ? "Success" : "Error");
        builder.setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();

                if (isSuccess) {

                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }

}