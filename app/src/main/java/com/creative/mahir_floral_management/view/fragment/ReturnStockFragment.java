package com.creative.mahir_floral_management.view.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.CommonMethods;
import com.creative.mahir_floral_management.adapters.ReturnStockAdapter;
import com.creative.mahir_floral_management.adapters.ShopIncomingStockAdapter;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentReturnStockBinding;
import com.creative.mahir_floral_management.databinding.FragmentShopIncomingStocksBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ReturnStock;
import com.creative.mahir_floral_management.model.ShopStock;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.ReturnStockViewModel;
import com.creative.mahir_floral_management.viewmodel.ShopIncomingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReturnStockFragment extends BaseFragment implements ReturnStockAdapter.OnItemClickListener {

    private FragmentReturnStockBinding binding;

    private int shop_id = 0;

    private ReturnStockAdapter adapter;
    private List<ReturnStock> returnStocks = new ArrayList<>(0);
    private ReturnStock selectedItem;
    private int selectedItemPosition;

    private AlertDialog alertDialog;


    public ReturnStockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == getArguments()) return;
        shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_stock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ReturnStockViewModel.class));
        //binding.getViewModel().setShopID(shop_id);
        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new ReturnStockAdapter(returnStocks, this);
        binding.rvResults.setAdapter(adapter);

        binding.getViewModel().validationLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showLongToast(s);
            }
        });

        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<ReturnStock>>() {
            @Override
            public void onChanged(@Nullable List<ReturnStock> stockList) {

                returnStocks.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    returnStocks.addAll(stockList);

                //Notify the adapter
                adapter.notifyDataSetChanged();

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

        binding.getViewModel().markReceivedLiveData.observe(this, new Observer<BaseModel>() {
            @Override
            public void onChanged(@Nullable BaseModel baseModel) {

                if (baseModel == null) return;

                if (baseModel.getStatus()) {

                    //Remove the item from the list
                    if (null != selectedItem) {

                       // returnStocksv.remove(selectedItem);
                        selectedItem.setStatus("1");
                        returnStocks.get(selectedItemPosition).setStatus("1");
                        returnStocks.get(selectedItemPosition).setReceivedDate(CommonMethods.getCurrentDate(GlobalAppAccess.MOBILE_DATE_FORMAT));
                        adapter.notifyDataSetChanged();
                        selectedItem = null;
                    }

                }

                showLongToast(baseModel.getMessage());

            }
        });

        binding.getViewModel().searchText.observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

                adapter.getFilter().filter(charSequence);
            }
        });

        return binding.getRoot();
    }



    @Override
    public void onItemClick(int itemPosition, ReturnStock item) {
        String role = MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getRole();
        if(role.equals(GlobalAppAccess.ROLE_RAW_STOCKER)){
            markReceivedAlert(itemPosition, item);
        }else{
            AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_ERROR,
                    "You are logged in as " + role + " user. In order to receive Return stocks you need to login as Raw Stock user.");
        }
    }

    private void markReceivedAlert(final  int itemPosition, final ReturnStock item) {

        selectedItem = item;
        selectedItemPosition = itemPosition;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_mark_as_received)
                .setMessage(String.format(Locale.getDefault(), getString(R.string.msg_mark_as_received), item.getProductName()))
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        binding.getViewModel().markReceiveReturnStock(item);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog = builder.create();
        alertDialog.show();

    }
}
