package com.creative.mahir_floral_management.view.fragment;

import android.app.ProgressDialog;
import androidx.fragment.app.DialogFragment;
import android.widget.Toast;

public class BaseDialogFragment extends DialogFragment {

    private ProgressDialog progressDialog;

    protected void showLongToast(int msgID, String msg) {
        Toast.makeText(getContext(), (0 == msgID) ? msg : getString(msgID), Toast.LENGTH_LONG).show();
    }

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
