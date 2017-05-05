package com.example.lubomir.kursovproektoop2.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.fragments.BuyTicketFragment;
import com.example.lubomir.kursovproektoop2.fragments.BuyerChooseFragment;
import com.example.lubomir.kursovproektoop2.fragments.FlightsListFragment;
import com.example.lubomir.kursovproektoop2.fragments.BuyerMapFragment;
import com.example.lubomir.kursovproektoop2.interfaces.BuyerActivityListener;
import com.example.lubomir.kursovproektoop2.models.Airport;
import com.example.lubomir.kursovproektoop2.models.Database;
import com.example.lubomir.kursovproektoop2.models.Flight;
import com.example.lubomir.kursovproektoop2.models.Ticket;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class BuyerActivity extends BaseActivity implements BuyerActivityListener {

    //Database instance
    Database database = Database.getInstance(this);

    //Fragment elements
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //Toolbar elements
    Toolbar mToolbar;
    TextView mToolbarTitle;

    //Data parameters
    String mUserNickname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_base_activity);

        initializeLayoutElements();

        getPassedData();

        loadBuyerChooseFragment();
    }

    /**
     * Method which initialize activities elements
     */
    void initializeLayoutElements() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
    }

    /**
     * Method which get the data that was passed to the activity
     */
    void getPassedData() {
        Intent intent = getIntent();
        mUserNickname = intent.getStringExtra("nickname");
    }

    /**
     * Method which load BuyerChooseFragment and set toolbar title
     */
    @Override
    public void loadBuyerChooseFragment() {
        fragment = new BuyerChooseFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "BuyerChooseFragment");
        fragmentTransaction.addToBackStack("BuyerChooseFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("BuyerChooseFragment");
    }

    /**
     * Method which load FlightsListFragment and set toolbar title
     */
    @Override
    public void loadFlightsListFragment() {
        fragment = new FlightsListFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "FlightsListFragment");
        fragmentTransaction.addToBackStack("FlightsListFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("FlightsListFragment");
    }

    /**
     * Method which load BuyTicketFragment and set toolbar title and pass data by bundle
     */
    @Override
    public void loadBuyTicketFragment(Bundle bundle) {
        fragment = new BuyTicketFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "BuyTicketFragment");
        fragmentTransaction.addToBackStack("BuyTicketFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("BuyTicketFragment");
    }

    /**
     * Method which load BuyerMapFragment and set toolbar title and pass data by bundle
     */
    @Override
    public void loadMapFragment(String fromOrToAirport, String fromAirport) {
        Bundle bundle = new Bundle();
        bundle.putString("fromOrToAirport", fromOrToAirport);
        bundle.putString("fromAirport", fromAirport);
        fragment = new BuyerMapFragment();
        fragment.setArguments(bundle);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "BuyerMapFragment");
        fragmentTransaction.addToBackStack("BuyerMapFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("BuyerMapFragment");
    }

    /**
     * Method which save current airport into database
     * @param airport
     */
    @Override
    public void createAirport(Airport airport) {
        try {
            database.addAirport(airport);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which gets list of all flights from database
     * @return
     */
    @Override
    public ArrayList<Flight> getFlights() {
        return database.getAllFlights();
    }

    /**
     * Method which create ticket and save it into database
     * @param fromAirport
     * @param toAirport
     * @param airplane
     * @param date
     * @param time
     * @param flightTime
     * @param price
     */
    @Override
    public void buyTicket(String fromAirport, String toAirport, String airplane, String date, String time, int flightTime, int price) {
        Ticket ticket = new Ticket();
        ticket.setFromAirport(fromAirport);
        ticket.setToAirport(toAirport);
        ticket.setAirplane(airplane);
        ticket.setDate(date);
        ticket.setTime(time);
        ticket.setMaxFlightTime(flightTime);
        ticket.setPrice(price);
        try {
            database.addTicket(ticket);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        clearBackStack();
        loadBuyerChooseFragment();
        Toast.makeText(this, "Закупихте билет успешно", Toast.LENGTH_LONG).show();
    }

    /**
     * Method that set current toolbar title by passed fragment name
     * @param fragmentName
     */
    void setToolbarTitleText(String fragmentName) {
        switch (fragmentName) {
            case "BuyerChooseFragment":
                mToolbarTitle.setText("Добре дошъл " + mUserNickname);
                break;
            case "FlightsListFragment":
                mToolbarTitle.setText("Списък с полети");
                break;
            case "BuyTicketFragment":
                mToolbarTitle.setText("Закупуване на билет");
                break;
            case "BuyerMapFragment":
                mToolbarTitle.setText("Изберете летище");
                break;
        }
    }

    /**
     * Method which set function to back button
     */
    @Override
    public void onBackPressed() {
        fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            showLogoutDialog(this);
        } else if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
            setToolbarTitleText(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 2).getName());
        } else {
            super.onBackPressed();
        }
    }
}
