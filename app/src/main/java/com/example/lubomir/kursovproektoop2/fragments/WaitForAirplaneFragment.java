package com.example.lubomir.kursovproektoop2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lubomir.kursovproektoop2.R;

import java.util.Random;

public class WaitForAirplaneFragment extends BaseFragment {


    final Random RANDOM = new Random();
    public static Handler handler;
    public Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wait_for_airplane_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler();runnable = new Runnable() {
            @Override
            public void run() {

                dispatcherCallback.loadAcceptOrCancelFragment();
            }
        };
        handler.postDelayed(runnable, getRandomTime());

    }

    /**
     * Method which return random number between 10 and 19
     * @return
     */
    int getRandomTime() {
        switch (RANDOM.nextInt(10)) {
            default:
            case 0:
                return 10000;
            case 1:
                return 11000;
            case 2:
                return 12000;
            case 3:
                return 13000;
            case 4:
                return 14000;
            case 5:
                return 15000;
            case 6:
                return 16000;
            case 7:
                return 17000;
            case 8:
                return 18000;
            case 9:
                return 19000;
        }
    }

    /**
     * Method which cancel current handler
     */
    public void cancelHandler() {
        handler.removeCallbacks(runnable);
    }
}
