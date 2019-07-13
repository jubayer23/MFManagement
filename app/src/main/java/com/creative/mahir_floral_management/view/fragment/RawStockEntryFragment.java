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

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.FragmentEnteryrawstockBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.viewmodel.RawStockEntryViewModel;

public class RawStockEntryFragment extends BaseFragment {

    private AlertDialog alertDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentEnteryrawstockBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enteryrawstock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(RawStockEntryViewModel.class));

        binding.getViewModel().setUnitArray(getResources().getStringArray(R.array.units));

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


        // Inflate the layout for this fragment
        return binding.getRoot();
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