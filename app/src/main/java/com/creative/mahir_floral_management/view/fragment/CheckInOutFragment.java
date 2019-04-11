package com.creative.mahir_floral_management.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.appdata.remote.ApiObserver;
import com.creative.mahir_floral_management.databinding.FragmentCheckInOutBinding;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.UserCheck;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.CheckInOutFragViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInOutFragment extends BaseFragment {

    private FragmentCheckInOutBinding fragmentCheckInOutBinding;
    private CheckInOutFragViewModel checkInOutFragViewModel;


    public CheckInOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        checkInOutFragViewModel = ViewModelProviders.of(this).get(CheckInOutFragViewModel.class);

        fragmentCheckInOutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_in_out, container, false);
        fragmentCheckInOutBinding.setLifecycleOwner(this);
        fragmentCheckInOutBinding.setCheckInOutFragViewModel(checkInOutFragViewModel);

        View view = fragmentCheckInOutBinding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkInOutFragViewModel.setUserProfile(MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile());


        observeCurrentOnlineStatus();

        observeUserInput();


    }

    private void observeUserInput() {
        checkInOutFragViewModel.getUserInput().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                LoginUser loginUser = MydApplication.getInstance().getPrefManger().getUserLoginInfo();

                if (loginUser.getPassword().equals(s)) {
                    observeCheckStatusChange();
                } else {
                    AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_ERROR, "Wrong password");
                }
            }
        });
    }


    private void observeCurrentOnlineStatus() {


        showProgressDialog("Loading...", true, false);

        checkInOutFragViewModel.getRemoteUserCurrentCheckStatus().observe(this, new ApiObserver<UserCheck>(new ApiObserver.ChangeListener<UserCheck>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(UserCheck userCheck) {


                dismissProgressDialog();

                if(userCheck.getStatus()){
                    checkInOutFragViewModel.setUserCurrentCheckStatus(Integer.parseInt(userCheck.getOnline()), userCheck.getLastCheckedIn());

                    updateUIBasedOnUserCheckStatus(userCheck.getOnline());
                }else{
                    AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", userCheck.getMessage(), false);
                }

            }

            @Override
            public void onException(VolleyError volleyError) {
                dismissProgressDialog();
                AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", "Network or Server response problem. Please try again later", false);
            }
        }));
    }


    private void observeCheckStatusChange() {

        showProgressDialog("Loading....", true, false);
        checkInOutFragViewModel.setRemoteUserCheckStatus().observe(this, new ApiObserver<UserCheck>(new ApiObserver.ChangeListener<UserCheck>() {
            @Override
            public void onSuccess(UserCheck userCheck) {
                dismissProgressDialog();
                if(userCheck.getStatus()){

                    checkInOutFragViewModel.setUserCurrentCheckStatus(Integer.parseInt(userCheck.getOnline()), userCheck.getLastCheckedIn());

                    updateUIBasedOnUserCheckStatus(userCheck.getOnline());

                    AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_SUCCESS, userCheck.getMessage());


                }else{
                    AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", userCheck.getMessage(), false);
                }


            }


            @Override
            public void onException(VolleyError volleyError) {
                dismissProgressDialog();
                AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", "Network or Server response problem. Please try again later", false);
            }
        }));
    }


    private void updateUIBasedOnUserCheckStatus(String is_online){

        fragmentCheckInOutBinding.edPassword.setText("");

        if(is_online.equals("1")){

        }else{

        }
    }
}
