package com.creative.mahir_floral_management.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.appdata.remote.ApiObserver;
import com.creative.mahir_floral_management.databinding.FragmentCheckInOutBinding;
import com.creative.mahir_floral_management.model.LoginUser;
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

        checkInOutFragViewModel.setUsername(MydApplication.getInstance().getPrefManger().getUserInfo().getName());


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

        checkInOutFragViewModel.getRemoteUserCurrentCheckStatus().observe(this, new ApiObserver<String>(new ApiObserver.ChangeListener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(String dataWrapper) {


                dismissProgressDialog();

                String value[] = dataWrapper.split("/");


                if (value.length > 1) {
                    checkInOutFragViewModel.setLastCheckIn(value[1]);
                } else {
                    fragmentCheckInOutBinding.tvLastCheckedInTitle.setVisibility(View.GONE);
                }

                if (value[0].equals("1")) {
                    checkInOutFragViewModel.setUserCurrentCheckStatus(1);
                    fragmentCheckInOutBinding.btnCheckInOut.setTextAppearance(R.style.btnErrorOrLogoutLarge);
                } else {
                    checkInOutFragViewModel.setUserCurrentCheckStatus(0);
                    fragmentCheckInOutBinding.btnCheckInOut.setTextAppearance(R.style.btnSubmitOrDoneLarge);
                }
            }

            @Override
            public void onFailure(String failureMessage) {

                dismissProgressDialog();
                AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", failureMessage, false);

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
        checkInOutFragViewModel.setRemoteUserCheckStatus().observe(this, new ApiObserver<Boolean>(new ApiObserver.ChangeListener<Boolean>() {
            @Override
            public void onSuccess(Boolean dataWrapper) {
                dismissProgressDialog();
                if (dataWrapper) {

                    String msg = "";

                    if (checkInOutFragViewModel.getUserCurrentCheckStatus() == 1) {
                        checkInOutFragViewModel.setUserCurrentCheckStatus(0);
                        msg = "You successfully checked out!";
                    } else {
                        checkInOutFragViewModel.setUserCurrentCheckStatus(1);
                        msg = "You successfully checked In!";
                    }


                    AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_SUCCESS, msg);

                } else {
                    AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", "Failed", false);
                }

            }

            @Override
            public void onFailure(String failureMessage) {
                dismissProgressDialog();
                AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", failureMessage, false);

            }

            @Override
            public void onException(VolleyError volleyError) {
                dismissProgressDialog();
                AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Alert", "Network or Server response problem. Please try again later", false);
            }
        }));
    }
}
