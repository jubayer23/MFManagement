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
import com.creative.mahir_floral_management.databinding.FragmentAdminMenuBinding;
import com.creative.mahir_floral_management.databinding.FragmentHqBinding;
import com.creative.mahir_floral_management.view.activity.DemandedStocksActivity;
import com.creative.mahir_floral_management.view.activity.RawStockActivity;
import com.creative.mahir_floral_management.view.activity.ReadyStockActivity;
import com.creative.mahir_floral_management.view.activity.StaffRegistrationActivity;
import com.creative.mahir_floral_management.view.activity.TimeSheetActivity;
import com.creative.mahir_floral_management.viewmodel.AdminMenuFragViewModel;
import com.creative.mahir_floral_management.viewmodel.HQFragViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminMenuFragment extends Fragment {

    private FragmentAdminMenuBinding binding;

    public AdminMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_admin_menu,container,false);
        binding.setViewModel(ViewModelProviders.of(this).get(AdminMenuFragViewModel.class));
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        observerUserClickEvent();
    }

    private void observerUserClickEvent(){
        binding.getViewModel().getUserButtonClickEvent().observe(this, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                int id = view.getId();
                if(id == binding.btnTimeSheet.getId()){

                    startActivity(new Intent(getActivity(), TimeSheetActivity.class));

                }else if(id == binding.btnStaffRegistration.getId()){

                    startActivity(new Intent(getActivity(), StaffRegistrationActivity.class));

                }
            }
        });

    }


}
