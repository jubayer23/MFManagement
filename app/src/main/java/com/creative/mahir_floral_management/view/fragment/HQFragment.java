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
import com.creative.mahir_floral_management.databinding.FragmentHqBinding;
import com.creative.mahir_floral_management.databinding.FragmentTimeSheetBinding;
import com.creative.mahir_floral_management.view.activity.CheckInOutActivity;
import com.creative.mahir_floral_management.view.activity.TimeSheets;
import com.creative.mahir_floral_management.viewmodel.HQFragViewModel;
import com.creative.mahir_floral_management.viewmodel.TimeSheetFragViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HQFragment extends Fragment {

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

                    startActivity(new Intent(getActivity(), TimeSheets.class));

                }else if(id == fragmentHqBinding.btnRawStock.getId()){

                }else if(id == fragmentHqBinding.btnReadyStock.getId()){
                   // startActivity(new Intent(getActivity(), CheckInOutActivity.class));
                }
            }
        });

    }


}
