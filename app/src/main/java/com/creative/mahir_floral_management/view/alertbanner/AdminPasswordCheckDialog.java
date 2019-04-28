package com.creative.mahir_floral_management.view.alertbanner;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.MydApplication;

public class AdminPasswordCheckDialog {

    public void showNotifyDialog(final Context context, final callBack callback){
        final Dialog dialog_start = new Dialog(context,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_start.setCancelable(true);
        dialog_start.setContentView(R.layout.dialog_admin_password_check);
        final EditText ed_password = dialog_start.findViewById(R.id.ed_password);
        Button btn_submit = dialog_start.findViewById(R.id.btn_submit);
        Button btn_cancel = dialog_start.findViewById(R.id.btn_cancel);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_password.getText().toString().isEmpty()){
                    ed_password.setError("Required!");
                    return;
                }

                if(ed_password.getText().toString().equals(MydApplication.getInstance().getPrefManger().getUserLoginInfo().getPassword())){
                    callback.success();
                    dialog_start.dismiss();
                }else{
                    //callback.failure();

                    Toast.makeText(context, "Password does not matched", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_start.dismiss();
            }
        });



        dialog_start.show();


    }

    public interface callBack{
        public void success();
    }
}
