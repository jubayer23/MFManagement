package com.creative.mahir_floral_management.view.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentHomeBinding;
import com.creative.mahir_floral_management.view.activity.CheckInOutActivity;
import com.creative.mahir_floral_management.view.activity.HQActivity;
import com.creative.mahir_floral_management.view.activity.LoginActivity;
import com.creative.mahir_floral_management.view.activity.ShopActivity;
import com.creative.mahir_floral_management.viewmodel.HomeFragViewModel;
import com.google.firebase.messaging.FirebaseMessaging;


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
                    startActivity(new Intent(getActivity(), HQActivity.class));
                }else if(id == fragmentHomeBinding.btnOutletConnecticut.getId()){

                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, GlobalAppAccess.SHOP_ID_CONNECTICUT);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_NAME, fragmentHomeBinding.btnOutletConnecticut.getText().toString());
                    startActivity(intent);

                }else if(id == fragmentHomeBinding.btnOutletEast.getId()){

                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, GlobalAppAccess.SHOP_ID_EAST);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_NAME, fragmentHomeBinding.btnOutletEast.getText().toString());
                    startActivity(intent);

                }else if(id == fragmentHomeBinding.btnOutletWest.getId()){

                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, GlobalAppAccess.SHOP_ID_WEST);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_NAME, fragmentHomeBinding.btnOutletWest.getText().toString());
                    startActivity(intent);

                }else if(id == fragmentHomeBinding.btnOutletVillage.getId()){

                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, GlobalAppAccess.SHOP_ID_VILLAGE);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_NAME, fragmentHomeBinding.btnOutletVillage.getText().toString());
                    startActivity(intent);

                }else if(id == fragmentHomeBinding.btnOutletHudsonYeards.getId()){

                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, GlobalAppAccess.SHOP_ID_HUDSONYARD);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_NAME, fragmentHomeBinding.btnOutletHudsonYeards.getText().toString());
                    startActivity(intent);

                }else if(id == fragmentHomeBinding.btnCheckInOut.getId()){
                    startActivity(new Intent(getActivity(), CheckInOutActivity.class));
                }else if(id == fragmentHomeBinding.btnLogout.getId()){

                    FirebaseMessaging.getInstance().unsubscribeFromTopic(MydApplication.getInstance().getPrefManger().getUserInfo().getUserProfile().getRole());
                    MydApplication.getInstance().getPrefManger().setUserLoginInfo(null);
                    MydApplication.getInstance().getPrefManger().setAccessToekn("");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        });

    }
}
