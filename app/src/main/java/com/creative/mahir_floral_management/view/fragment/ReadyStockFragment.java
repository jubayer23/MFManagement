package com.creative.mahir_floral_management.view.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.ReadyStockAdapter;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentReadystockBinding;
import com.creative.mahir_floral_management.model.ReadyStock;
import com.creative.mahir_floral_management.view.activity.RawStockEntryActivity;
import com.creative.mahir_floral_management.view.activity.ReadyStockEntryActivity;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.ReadyStockViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ReadyStockFragment extends BaseFragment implements ReadyStockAdapter.OnItemClickListener {

    private FragmentReadystockBinding binding;

    private ReadyStockAdapter readyStockAdapter;
    private List<ReadyStock> readyStocks = new ArrayList<>(0);
    private ReadyStock selectedItem;

    private final int rawEntrySuccess = 1002;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_readystock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ReadyStockViewModel.class));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        readyStockAdapter = new ReadyStockAdapter(readyStocks, this);
        binding.rvResults.setAdapter(readyStockAdapter);

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
                readyStockAdapter.notifyDataSetChanged();

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

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String role = MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getRole();
                if(role.equals(GlobalAppAccess.ROLE_RAW_STOCKER)){
                    startActivityForResult(new Intent(getActivity(), ReadyStockEntryActivity.class), rawEntrySuccess);
                }else{
                    AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_ERROR,
                            "You are logged in as" + role + " User. In order to add ready stocks you need to login as Raw Stock user.");
                }

            }
        });

        binding.getViewModel().searchText.observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

                readyStockAdapter.getFilter().filter(charSequence);
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onItemClick(ReadyStock item) {


        String role = MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getRole();
        if(role.equals(GlobalAppAccess.ROLE_RAW_STOCKER)){
            selectedItem = item;
            showDeliverDialog(item);
        }else{
            AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_ERROR,
                    "You are logged in as " + role + " user. In order to deliver stocks you need to login as Raw Stock user.");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == rawEntrySuccess) {
            binding.getViewModel().requestToRefresh();
        }

    }

    public void refreshScreen(int remainingQty) {

        if (null == selectedItem) return;

        if (remainingQty <= 0) {
            readyStocks.remove(selectedItem);
        } else {

            int index = readyStocks.indexOf(selectedItem);
            ReadyStock readyStock = readyStocks.get(index);

            readyStock.setQuantity(String.valueOf(remainingQty));
            readyStocks.set(index, readyStock);
        }

        readyStockAdapter.notifyDataSetChanged();

        selectedItem = null;

    }

    private void showDeliverDialog(final ReadyStock item) {

        ReadyStockDeliverDialogFragment editNameDialog = new ReadyStockDeliverDialogFragment();

        Bundle data = new Bundle();
        data.putParcelable("stockData", item);

        editNameDialog.setArguments(data);
        editNameDialog.setTargetFragment(ReadyStockFragment.this, 1337);
        editNameDialog.show(getFragmentManager(), "fragment_edit_name");
    }
}
