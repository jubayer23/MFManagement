package com.creative.mahir_floral_management.view.fragment;


import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.GpsEnableTool;
import com.creative.mahir_floral_management.Utility.RunnTimePermissions;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.appdata.remote.ApiObserver;
import com.creative.mahir_floral_management.databinding.FragmentCheckInOutBinding;
import com.creative.mahir_floral_management.model.LoginUser;
import com.creative.mahir_floral_management.model.UserCheck;
import com.creative.mahir_floral_management.model.UserInfo;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.CheckInOutFragViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInOutFragment extends BaseFragment {

    private FragmentCheckInOutBinding fragmentCheckInOutBinding;
    private CheckInOutFragViewModel checkInOutFragViewModel;

    private FusedLocationProviderClient mFusedLocationClient;


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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        observeCurrentOnlineStatus();

        observeUserInput();


    }

    private void observeUserInput() {
        checkInOutFragViewModel.getUserInput().observe(this, new Observer<String>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onChanged(@Nullable String s) {
                LoginUser loginUser = MydApplication.getInstance().getPrefManger().getUserLoginInfo();
                final UserInfo userInfo = MydApplication.getInstance().getPrefManger().getUserInfo();

                if (!loginUser.getPassword().equals(s)) {
                    AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_ERROR, "Wrong password");
                    return;
                }

                if (!RunnTimePermissions.requestForAllRuntimePermissions(getActivity())) {
                    return;
                }


                showProgressDialog("Loading...", true,false);
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    // zoomToSpecificLocation(new LatLng(location.getLatitude(), location.getLongitude()));

                                    float[] distance = new float[1];
                                    Location.distanceBetween(location.getLatitude(),
                                            location.getLongitude()
                                            , userInfo.getUserProfile().getShopLat(),
                                            userInfo.getUserProfile().getShopLong(), distance);
                                    // distance[0] is now the distance between these lat/lons in meters
                                    //Log.d("DEBUG_D", String.valueOf(distance[0]));
                                    if (distance[0] < 100.0) {
                                        // your code...
                                        observeCheckStatusChange();
                                    }else{
                                        dismissProgressDialog();
                                        AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(),"Alert!", " In order to Check-In or Check-Out you must need to inside the corresponding shop!", false);
                                    }
                                } else {
                                    //location = null means the location service is turned off in user device. Lets ask them to turn it on
                                    dismissProgressDialog();
                                    GpsEnableTool gpsEnableTool = new GpsEnableTool(getActivity());
                                    gpsEnableTool.enableGPs();
                                    return;
                                }
                            }
                        });


               // observeCheckStatusChange();


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

                if (userCheck.getStatus()) {
                    checkInOutFragViewModel.setUserCurrentCheckStatus(Integer.parseInt(userCheck.getOnline()), userCheck.getLastCheckedIn());

                    updateUIBasedOnUserCheckStatus(userCheck.getOnline());
                } else {
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
                if (userCheck.getStatus()) {

                    checkInOutFragViewModel.setUserCurrentCheckStatus(Integer.parseInt(userCheck.getOnline()), userCheck.getLastCheckedIn());

                    updateUIBasedOnUserCheckStatus(userCheck.getOnline());

                    AlertDialogForAnything.showNotifyDialog(getActivity(), AlertDialogForAnything.ALERT_TYPE_SUCCESS, userCheck.getMessage());


                } else {
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


    private void updateUIBasedOnUserCheckStatus(String is_online) {

        fragmentCheckInOutBinding.edPassword.setText("");

        if (is_online.equals("1")) {

        } else {

        }
    }
}
