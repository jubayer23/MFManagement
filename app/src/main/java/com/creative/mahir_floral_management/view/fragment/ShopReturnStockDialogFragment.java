package com.creative.mahir_floral_management.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.FragmentDialogReturnStockBinding;
import com.creative.mahir_floral_management.databinding.FragmentDialogSoldStockBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ShopStock;
import com.creative.mahir_floral_management.viewmodel.ShopRetunStockDialogViewModel;
import com.creative.mahir_floral_management.viewmodel.SoldStockDialogViewModel;

import java.util.Locale;

public class ShopReturnStockDialogFragment extends BaseDialogFragment {

    private FragmentDialogReturnStockBinding binding;
    private ShopStock shopStock;
    private String shop_id;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            shopStock = getArguments().getParcelable("shopStockData");
            shop_id = getArguments().getString("shopId");

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_return_stock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ShopRetunStockDialogViewModel.class));
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

                    ShopStocksFragment stockFragment = ((ShopStocksFragment) getTargetFragment());
                    if (null != stockFragment) {

                        int qtyDelivered = binding.getViewModel().getProductQuantityReturned();
                        int totalQty = Integer.parseInt(shopStock.getQuantity());

                        stockFragment.refreshScreen(totalQty - qtyDelivered);
                    }

                }

            }
        });

        //Fill data
        setFields();

        return binding.getRoot();
    }

    private void setFields() {

        if (null == shopStock) {
            dismiss();
            return;
        }

        binding.getViewModel().setShopStock(shopStock);
        binding.getViewModel().setShopId(shop_id);

        binding.tvProductName.setText(shopStock.getProductName());
        binding.tvProductQty.setText(String.format(Locale.getDefault(), getString(R.string.total_quantity), shopStock.getQuantity(), shopStock.getUnit()));
        binding.tvProductUnit.setText(shopStock.getUnit());

    }



}
