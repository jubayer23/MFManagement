package com.creative.mahir_floral_management.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentHqBinding;
import com.creative.mahir_floral_management.view.activity.AdminMenuActivity;
import com.creative.mahir_floral_management.view.activity.DemandedStocksActivity;
import com.creative.mahir_floral_management.view.activity.RawStockActivity;
import com.creative.mahir_floral_management.view.activity.ReadyStockActivity;
import com.creative.mahir_floral_management.view.activity.TimeSheetActivity;
import com.creative.mahir_floral_management.view.alertbanner.AdminPasswordCheckDialog;
import com.creative.mahir_floral_management.viewmodel.HQFragViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HQFragment extends BaseFragment {

    private FragmentHqBinding fragmentHqBinding;
    private HQFragViewModel hqFragViewModel;

    public HQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        hqFragViewModel = ViewModelProviders.of(this).get(HQFragViewModel.class);

        fragmentHqBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_hq,container,false);
        fragmentHqBinding.setLifecycleOwner(this);
        fragmentHqBinding.setHQFragViewModel(hqFragViewModel);

        View view = fragmentHqBinding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        observerUserClickEvent();
    }

    private void observerUserClickEvent(){
        hqFragViewModel.getUserButtonClickEvent().observe(this, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                int id = view.getId();
                if(id == fragmentHqBinding.btnAdmin.getId()){

                    if(!MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getRole().equals(GlobalAppAccess.ROLE_ADMIN)){
                        showLongToast("Only admin can access this link");
                        return;
                    }

                    AdminPasswordCheckDialog dialog = new AdminPasswordCheckDialog();
                    dialog.showNotifyDialog(getActivity(), new AdminPasswordCheckDialog.callBack() {
                        @Override
                        public void success() {
                            startActivity(new Intent(getActivity(), AdminMenuActivity.class));
                        }
                    });



                }else if(id == fragmentHqBinding.btnRawStock.getId()){

                    startActivity(new Intent(getActivity(), RawStockActivity.class));

                }else if(id == fragmentHqBinding.btnReadyStock.getId()){

                    startActivity(new Intent(getActivity(), ReadyStockActivity.class));
                }else if(id == fragmentHqBinding.btnDemandedStocks.getId()){

                    startActivity(new Intent(getActivity(), DemandedStocksActivity.class));
                }
            }
        });

    }


}
