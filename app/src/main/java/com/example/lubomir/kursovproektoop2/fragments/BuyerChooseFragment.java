package com.example.lubomir.kursovproektoop2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lubomir.kursovproektoop2.R;

public class BuyerChooseFragment extends BaseFragment {

    Button mFlightsList, mBuyTicket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.buyer_choose_fragment, container, false);
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
        mFlightsList = (Button) view.findViewById(R.id.btnList);
        mBuyTicket = (Button) view.findViewById(R.id.btnTicket);
    }

    /**
     * Method which set fragment views click listeners
     */
    void setOnClickListeners() {
        mFlightsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyerCallback.loadFlightsListFragment();
            }
        });

        mBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyerCallback.loadBuyTicketFragment(null);
            }
        });
    }

}
