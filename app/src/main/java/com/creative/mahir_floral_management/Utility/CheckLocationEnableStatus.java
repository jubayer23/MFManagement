package com.creative.mahir_floral_management.Utility;

/**
 * Created by jubayer on 5/9/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;

/**
 * Created by comsol on 18-May-16.
 */
public class CheckLocationEnableStatus {
    private Context mContext;
    // Declaring a Location Manager
    protected LocationManager locationManager;


    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;


    public CheckLocationEnableStatus(Context context) {
        this.mContext = context;
        getLocation();
    }


    @SuppressLint("ServiceCast")
    public void getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

}