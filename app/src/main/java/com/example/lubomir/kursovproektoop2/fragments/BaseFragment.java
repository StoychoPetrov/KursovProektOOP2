package com.example.lubomir.kursovproektoop2.fragments;

import android.app.Activity;
import android.app.Fragment;

import com.example.lubomir.kursovproektoop2.interfaces.BaseActivityListener;
import com.example.lubomir.kursovproektoop2.interfaces.BuyerActivityListener;
import com.example.lubomir.kursovproektoop2.interfaces.DispatcherActivityListener;
import com.example.lubomir.kursovproektoop2.interfaces.LoginActivityListener;

public class BaseFragment extends Fragment {

    //Activities listeners callbacks
    BaseActivityListener baseCallback;
    LoginActivityListener loginCallback;
    BuyerActivityListener buyerCallback;
    DispatcherActivityListener dispatcherCallback;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivityListener) {
            baseCallback = (BaseActivityListener) activity;
        }
        if(activity instanceof LoginActivityListener) {
            loginCallback = (LoginActivityListener) activity;
        }
        if(activity instanceof BuyerActivityListener) {
            buyerCallback = (BuyerActivityListener) activity;
        }
        if(activity instanceof DispatcherActivityListener) {
            dispatcherCallback = (DispatcherActivityListener) activity;
        }
    }
}

