package com.example.lubomir.kursovproektoop2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lubomir.kursovproektoop2.R;

public class DispatcherChooseFragment extends BaseFragment {

    //Layout elements
    Button mAcceptFlight, mSetFlight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dispatcher_choose_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayoutElements(view);

        setOnClickListeners();
    }

    /**
     * Method which initialize fragment elements
     * @param view
     */
    private void initializeLayoutElements(View view) {
        mAcceptFlight = (Button) view.findViewById(R.id.btnAccept);
        mSetFlight = (Button) view.findViewById(R.id.btnSetFlight);
    }

    /**
     * Method which set fragment views click listeners
     */
    void setOnClickListeners() {
        mAcceptFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatcherCallback.loadWaitForAirplaneFragment();
            }
        });

        mSetFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatcherCallback.loadCreateFlightFragment(null);
            }
        });
    }

}
