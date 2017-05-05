package com.example.lubomir.kursovproektoop2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.adapters.FlightsAdapter;
import com.example.lubomir.kursovproektoop2.models.Flight;

import java.util.ArrayList;

public class FlightsListFragment extends BaseFragment {

    //Layout elements
    ListView mFlightsListView;
    ArrayList<Flight> mFlightsList;
    FlightsAdapter mFlightsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.flights_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayoutElements(view);

        addFlightsToList();

        loadFlightsAdapter();

    }

    /**
     * Method which initialize fragment elements
     * @param view
     */
    void initializeLayoutElements(View view) {
        mFlightsListView = (ListView) view.findViewById(R.id.lvFlights);
        mFlightsList = new ArrayList<Flight>();
        mFlightsList.clear();
    }

    /**
     * Method which loads flights into ArrayList
     */
    void addFlightsToList() {
        mFlightsList.add(new Flight("От:", "До:", "Самолет:", "Дата:", "Час:", "Време:"));
        for (int i = 0; i < buyerCallback.getFlights().size(); i++) {
            mFlightsList.add(buyerCallback.getFlights().get(i));
        }
    }

    /**
     * Method which loads flights into ListView with custom flights adapters
     */
    void loadFlightsAdapter() {
        mFlightsAdapter = new FlightsAdapter(getActivity(), mFlightsList);
        mFlightsListView.setAdapter(mFlightsAdapter);
    }
}
