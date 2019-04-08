package com.creative.mahir_floral_management.view.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    private ProgressDialog progressDialog;

    public void showProgressDialog(String message, boolean isIntermidiate, boolean isCancelable) {
        /**/
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        if (progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            return;
        }
        progressDialog.setIndeterminate(isIntermidiate);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
