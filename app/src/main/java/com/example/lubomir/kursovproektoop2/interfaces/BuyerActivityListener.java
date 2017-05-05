package com.example.lubomir.kursovproektoop2.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.lubomir.kursovproektoop2.models.Airport;
import com.example.lubomir.kursovproektoop2.models.Flight;

import java.util.ArrayList;

public interface BuyerActivityListener {

    void loadBuyerChooseFragment();
    void loadFlightsListFragment();
    void loadBuyTicketFragment(@Nullable Bundle bundle);
    void loadMapFragment(String fromOrToAirport, @Nullable String fromAirport);
    void createAirport(Airport airport);
    ArrayList<Flight> getFlights();
    void buyTicket(String fromAirport, String toAirport, String airplane, String date, String time, int flightTime, int price);
}
