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
import com.example.lubomir.kursovproektoop2.fragments.AcceptOrCancelFlightFragment;
import com.example.lubomir.kursovproektoop2.fragments.CreateFlightFragment;
import com.example.lubomir.kursovproektoop2.fragments.DispatcherMapFragment;
import com.example.lubomir.kursovproektoop2.fragments.DispatcherChooseFragment;
import com.example.lubomir.kursovproektoop2.fragments.WaitForAirplaneFragment;
import com.example.lubomir.kursovproektoop2.interfaces.DispatcherActivityListener;
import com.example.lubomir.kursovproektoop2.models.Airport;
import com.example.lubomir.kursovproektoop2.models.Database;
import com.example.lubomir.kursovproektoop2.models.Flight;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class DispatcherActivity extends BaseActivity implements DispatcherActivityListener {

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
        setContentView(R.layout.dispatcher_activity);

        initializeLayoutElements();

        getPassedData();

        loadDispatcherChooseFragment();
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
     * Method which load DispatcherChooseFragment and set toolbar title and clear all fragments from
     * back stack
     */
    @Override
    public void loadDispatcherChooseFragment() {
        clearBackStack();
        fragment = new DispatcherChooseFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "DispatcherChooseFragment");
        fragmentTransaction.addToBackStack("DispatcherChooseFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("DispatcherChooseFragment");
    }

    /**
     * Method which load WaitForAirplaneFragment and set toolbar title
     */
    @Override
    public void loadWaitForAirplaneFragment() {
        fragment = new WaitForAirplaneFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "WaitForAirplaneFragment");
        fragmentTransaction.addToBackStack("WaitForAirplaneFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("WaitForAirplaneFragment");
    }

    /**
     * Method which load CreateFlightFragment and set toolbar title and pass data by bundle
     */
    @Override
    public void loadCreateFlightFragment(Bundle bundle) {
        fragment = new CreateFlightFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "CreateFlightFragment");
        fragmentTransaction.addToBackStack("CreateFlightFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("CreateFlightFragment");
    }

    /**
     * Method which load DispatcherMapFragment and set toolbar title and pass data by bundle
     */
    @Override
    public void loadMapFragment(String fromOrToAirport, String fromAirport) {
        Bundle bundle = new Bundle();
        bundle.putString("fromOrToAirport", fromOrToAirport);
        bundle.putString("fromAirport", fromAirport);
        fragment = new DispatcherMapFragment();
        fragment.setArguments(bundle);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "DispatcherMapFragment");
        fragmentTransaction.addToBackStack("DispatcherMapFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("DispatcherMapFragment");
    }

    /**
     * Method which load AcceptOrCancelFlightFragment and set toolbar title
     */
    @Override
    public void loadAcceptOrCancelFragment() {
        fragment = new AcceptOrCancelFlightFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment, "AcceptOrCancelFlightFragment");
        fragmentTransaction.addToBackStack("AcceptOrCancelFlightFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("AcceptOrCancelFlightFragment");
    }

    /**
     * Method which save current airport to database
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
     * Method which create new flight and save it into database, clear all fragments from back stack
     * and load DispatcherChooseFragment
     * @param fromAirport
     * @param toAirport
     * @param airplane
     * @param date
     * @param time
     * @param flightTime
     */
    @Override
    public void createFlight(String fromAirport, String toAirport, String airplane, String date, String time, int flightTime) {
        Flight flight = new Flight(fromAirport, toAirport, airplane, date, time, flightTime);
        try {
            database.addFlight(flight);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        clearBackStack();
        loadDispatcherChooseFragment();
        Toast.makeText(this, "Полетът беше създаден успешно", Toast.LENGTH_LONG).show();
    }

    /**
     * Method that set current toolbar title by passed fragment name
     * @param fragmentName
     */
    void setToolbarTitleText(String fragmentName) {
        switch (fragmentName) {
            case "DispatcherChooseFragment":
                mToolbarTitle.setText("Добре дошъл " + mUserNickname);
                break;
            case "WaitForAirplaneFragment":
                mToolbarTitle.setText("Изчакване на самолет");
                break;
            case "CreateFlightFragment":
                mToolbarTitle.setText("Създаване на полет");
                break;
            case "DispatcherMapFragment":
                mToolbarTitle.setText("Изберете летище");
                break;
            case "AcceptOrCancelFlightFragment":
                mToolbarTitle.setText("Приемане или отказване на самолет");
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
            if((fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName().equals("WaitForAirplaneFragment"))) {
                WaitForAirplaneFragment waitForAirplaneFragment = (WaitForAirplaneFragment) fragmentManager.findFragmentById(R.id.fragments);
                waitForAirplaneFragment.cancelHandler();
            }
        } else {
            super.onBackPressed();
        }
    }
}
