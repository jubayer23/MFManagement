package com.creative.mahir_floral_management.view.fragment;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.ShopAvailableReadyStockAdapter;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentShopAvailableReadyStockBinding;
import com.creative.mahir_floral_management.model.ReadyStock;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.ShopAvailableReadyStockViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopAvailableReadyStockFragment extends BaseFragment implements ShopAvailableReadyStockAdapter.OnItemClickListener {

    private FragmentShopAvailableReadyStockBinding binding;

    private int shop_id = 0;
    private String shop_name;
    private ShopAvailableReadyStockAdapter adapter;
    private List<ReadyStock> readyStocks = new ArrayList<>(0);
    private ReadyStock selectedItem;

    public ShopAvailableReadyStockFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == getArguments()) return;
        shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);
        shop_name = getArguments().getString(GlobalAppAccess.KEY_SHOP_NAME);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_shop_demand_stock, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_available_ready_stock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ShopAvailableReadyStockViewModel.class));
        binding.getViewModel().setShopID(shop_id);
        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new ShopAvailableReadyStockAdapter(readyStocks, this);
        binding.rvResults.setAdapter(adapter);


        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<ReadyStock>>() {
            @Override
            public void onChanged(@Nullable List<ReadyStock> stockList) {

                readyStocks.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    readyStocks.addAll(stockList);

                //Notify the adapter
                adapter.notifyDataSetChanged();

            }
        });

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

        /*binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ReadyStockEntryActivity.class), rawEntrySuccess);
            }
        });*/

        binding.getViewModel().searchText.observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

                adapter.getFilter().filter(charSequence);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onItemClick(ReadyStock item) {
        String role = MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getRole();
        if(role.equals(GlobalAppAccess.ROLE_SHOP_STOCKER)){
            selectedItem = item;
            showMakeDemandDialog(item);
        }else{
            AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_ERROR,
                    "You are logged in as " + role + " user. In order to make demand you need to login as Shop Stock user.");
        }
    }

    private void showMakeDemandDialog(final ReadyStock item) {

        MakeDemandDialogFragment editNameDialog = new MakeDemandDialogFragment();

        Bundle data = new Bundle();
        data.putParcelable("stockData", item);
        data.putInt(GlobalAppAccess.KEY_SHOP_ID, shop_id);
        data.putString(GlobalAppAccess.KEY_SHOP_NAME, shop_name);

        editNameDialog.setArguments(data);
        editNameDialog.setTargetFragment(ShopAvailableReadyStockFragment.this, 1337);
        editNameDialog.show(getFragmentManager(), "fragment_edit_name");
    }

    public void showSuccessDialog(){
        AlertDialogForAnything.showNotifyDialog(getActivity(),AlertDialogForAnything.ALERT_TYPE_SUCCESS, "Demand successful");
    }
}
