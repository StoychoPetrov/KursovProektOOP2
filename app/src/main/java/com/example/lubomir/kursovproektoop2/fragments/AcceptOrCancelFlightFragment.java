package com.example.lubomir.kursovproektoop2.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lubomir.kursovproektoop2.R;

public class AcceptOrCancelFlightFragment extends BaseFragment {

    //Layout elements
    Button mYes, mNo;

    //Pick type dialog attributes
    AlertDialog mPickTypeDialog;
    Button mOk;
    TextView mFlightResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.accept_or_cancel_flight_fragment, container, false);
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
    void initializeLayoutElements(View view) {
        mYes = (Button) view.findViewById(R.id.btnYes);
        mNo = (Button) view.findViewById(R.id.btnNo);
    }

    /**
     * Method which set fragment views click listeners
     */
    void setOnClickListeners() {
        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlightResultDialog("Полетът беше успешен");
            }
        });

        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlightResultDialog("Самолетът катострофира");
            }
        });
    }

    /**
     * Method which show result dialog. After user click OK , load DispatcherChooseFragment
     * @param result
     */
    void showFlightResultDialog(String result) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewBox = inflater.inflate(R.layout.flight_result_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewBox);

        mOk = (Button) viewBox.findViewById(R.id.btnOk);
        mFlightResult = (TextView) viewBox.findViewById(R.id.tvFlightResult);
        mFlightResult.setText(result);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatcherCallback.loadDispatcherChooseFragment();
                mPickTypeDialog.cancel();
            }
        });

        mPickTypeDialog = builder.create();
        mPickTypeDialog.show();
        mPickTypeDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, baseCallback.getDisplayHeight() / 3);

    }
}
