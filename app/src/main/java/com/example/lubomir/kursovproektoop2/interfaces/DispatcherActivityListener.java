package com.example.lubomir.kursovproektoop2.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.lubomir.kursovproektoop2.models.Airport;

public interface DispatcherActivityListener {

    void loadDispatcherChooseFragment();
    void loadWaitForAirplaneFragment();
    void loadCreateFlightFragment(Bundle bundle);
    void loadMapFragment(String fromOrToAirport, @Nullable String fromAirport);
    void loadAcceptOrCancelFragment();
    void createAirport(Airport airport);
    void createFlight(String fromAirport, String toAirport, String airplane, String date, String time, int flightTime);
}
