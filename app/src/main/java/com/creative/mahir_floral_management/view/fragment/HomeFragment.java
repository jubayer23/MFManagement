package com.creative.mahir_floral_management.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.FragmentHomeBinding;
import com.creative.mahir_floral_management.view.activity.CheckInOutActivity;
import com.creative.mahir_floral_management.viewmodel.HomeFragViewModel;
import com.creative.mahir_floral_management.viewmodel.LoginViewModel;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding fragmentHomeBinding;
    private HomeFragViewModel homeFragViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        homeFragViewModel = ViewModelProviders.of(this).get(HomeFragViewModel.class);

        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        fragmentHomeBinding.setLifecycleOwner(this);
        fragmentHomeBinding.setHomeFragViewModel(homeFragViewModel);

        View view = fragmentHomeBinding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        observerUserClickEvent();
    }

    private void observerUserClickEvent(){
        homeFragViewModel.getUserButtonClickEvent().observe(this, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                int id = view.getId();
                if(id == fragmentHomeBinding.btnOutletHq.getId()){

                }else if(id == fragmentHomeBinding.btnOutletConnecticut.getId()){

                }else if(id == fragmentHomeBinding.btnCheckInOut.getId()){
                    startActivity(new Intent(getActivity(), CheckInOutActivity.class));
                }
            }
        });

    }
}
