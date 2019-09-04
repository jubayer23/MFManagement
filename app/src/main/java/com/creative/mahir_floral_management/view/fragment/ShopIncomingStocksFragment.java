package com.creative.mahir_floral_management.view.fragment;


import android.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.ShopStockAdapter;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentShopIncomingStocksBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ShopStock;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.ShopIncomingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopIncomingStocksFragment extends BaseFragment implements ShopStockAdapter.OnItemClickListener {

    private FragmentShopIncomingStocksBinding binding;

    private int shop_id = 0;

    private ShopStockAdapter adapter;
    private List<ShopStock> shopStockList = new ArrayList<>(0);
    private ShopStock selectedItem;

    private AlertDialog alertDialog;

    public ShopIncomingStocksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == getArguments()) return;
        shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_incoming_stocks, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ShopIncomingViewModel.class));
        binding.getViewModel().setShopID(shop_id);
        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new ShopStockAdapter(shopStockList, this);
        binding.rvResults.setAdapter(adapter);

        binding.getViewModel().validationLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showLongToast(s);
            }
        });

        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<ShopStock>>() {
            @Override
            public void onChanged(@Nullable List<ShopStock> stockList) {

                shopStockList.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    shopStockList.addAll(stockList);

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

                        shopStockList.remove(selectedItem);
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
    public void onPause() {
        if (null != alertDialog)
            alertDialog.dismiss();

        super.onPause();
    }

    @Override
    public void onItemClick(ShopStock item) {

        String role = MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getRole();
        if(role.equals(GlobalAppAccess.ROLE_RAW_STOCKER)){
            markReceivedAlert(item);
        }else{
            AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_ERROR,
                    "You are logged in as " + role + " user. In order to receive stocks you need to login as Shop Stock user.");
        }

    }

    private void markReceivedAlert(final ShopStock item) {

        selectedItem = item;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_mark_as_received)
                .setMessage(String.format(Locale.getDefault(), getString(R.string.msg_mark_as_received), item.getProductName()))
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        binding.getViewModel().markReceive(item);
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
