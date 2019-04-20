package com.creative.mahir_floral_management.view.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.FragmentSoldstockBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ShopStock;
import com.creative.mahir_floral_management.viewmodel.ShopSoldStockViewModel;

import java.util.Locale;

public class SoldShopStockDialogFragment extends DialogFragment {

    private FragmentSoldstockBinding binding;
    private ShopStock shopStock;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            shopStock = getArguments().getParcelable("shopStockData");

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_soldstock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ShopSoldStockViewModel.class));
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

                        int qtyDelivered = binding.getViewModel().getProductQuantityDelivered();
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

        binding.tvProductName.setText(shopStock.getProductName());
        binding.tvProductQty.setText(String.format(Locale.getDefault(), getString(R.string.total_quantity), shopStock.getQuantity(), shopStock.getUnit()));
        binding.tvProductUnit.setText(shopStock.getUnit());

    }

    protected void showLongToast(int msgID, String msg) {
        Toast.makeText(getContext(), (0 == msgID) ? msg : getString(msgID), Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog(String message, boolean isIntermidiate, boolean isCancelable) {
        /**/
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        if (progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            return;
        }
        progressDialog.setIndeterminate(isIntermidiate);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
