package com.creative.mahir_floral_management.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.FragmentDialogMakeDemandBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ReadyStock;
import com.creative.mahir_floral_management.viewmodel.MakeDemandViewModel;

import java.util.Locale;

public class MakeDemandDialogFragment extends BaseDialogFragment {

    private FragmentDialogMakeDemandBinding binding;
    private ReadyStock stockData;
    private int shop_id = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            stockData = getArguments().getParcelable("stockData");
            shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_make_demand, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(MakeDemandViewModel.class));


        binding.getViewModel().setPriorityStringArray(getResources().getStringArray(R.array.priority));


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.getViewModel().validationLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {

                if (null == s) return;

                switch (s) {

                    //Product amount not entered
                    case 1:
                        showLongToast(R.string.msg_enter_amount, "");
                        break;
                    //Shop not selected
                    case 2:
                        showLongToast(R.string.msg_select_shop, "");
                        break;
                    //Amount is greater than the total
                    case 3:
                        showLongToast(R.string.msg_amount_grater, "");
                        break;

                    //Comment is not entered
                    case 4:
                        showLongToast(R.string.msg_empty_comment, "");
                        break;

                }

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

                showLongToast(0, baseModel.getMessage());

                if (baseModel.getStatus()) {

                    dismiss();

                    ShopAvailableReadyStockFragment shopAvailableReadyStockFragment = ((ShopAvailableReadyStockFragment) getTargetFragment());
                    if (null != shopAvailableReadyStockFragment) {

                        //SHOw SCUESS
                        //Log.d("DEBUG", "success");
                        shopAvailableReadyStockFragment.showSuccessDialog();
                    }

                }

            }
        });

        //Fill data
        setFields();

        return binding.getRoot();
    }

    private void setFields() {

        if (null == stockData) {
            dismiss();
            return;
        }

        binding.getViewModel().setStock(stockData);
        binding.getViewModel().setShopId(shop_id);



        binding.tvProductName.setText(stockData.getName());
        binding.tvProductQty.setText(String.format(Locale.getDefault(), getString(R.string.total_amount), stockData.getQuantity(), stockData.getUnit()));
        binding.tvProductUnit.setText(stockData.getUnit());

    }

}
