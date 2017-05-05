package com.example.lubomir.kursovproektoop2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.models.Airport;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DispatcherMapFragment extends BaseFragment implements GoogleMap.OnInfoWindowClickListener {

    //Map elements
    MapView gMapView;
    GoogleMap gMap = null;
    Marker sofiaMarker, varnaMarker, plovdivMarker, burgasMarker;
    LatLng sofiaLatLng, varnaLatLng, plovdivLatLng, burgasLatLng;
    Airport sofia, varna, plovdiv, burgas;

    //Data parameters
    String fromOrToAirport, fromAirport, toAirport;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        // Gets the MapView from the XML layout and creates it
        gMapView = (MapView) view.findViewById(R.id.mapView);
        gMapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        gMap = gMapView.getMap();
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43, 25.5), 6);
        gMap.animateCamera(cameraUpdate);

        gMap.setOnInfoWindowClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createAirports();

        getPassedData();
    }

    /**
     * Method which create new airports and save them into airport
     */
    private void createAirports() {
        sofia = new Airport();
        sofia.setLatitude(42.70);
        sofia.setLongitude(23.30);
        sofia.setBerths(10);
        sofia.setName("Летище София");
        sofia.setPlaces(20);
        dispatcherCallback.createAirport(sofia);

        varna = new Airport();
        varna.setLatitude(43.20);
        varna.setLongitude(27.90);
        varna.setBerths(8);
        varna.setName("Летище Варна");
        varna.setPlaces(15);
        dispatcherCallback.createAirport(varna);

        plovdiv = new Airport();
        plovdiv.setLatitude(42.15);
        plovdiv.setLongitude(24.80);
        plovdiv.setBerths(6);
        plovdiv.setName("Летище Пловдив");
        plovdiv.setPlaces(12);
        dispatcherCallback.createAirport(plovdiv);

        burgas = new Airport();
        burgas.setLatitude(42.50);
        burgas.setLongitude(27.47);
        burgas.setBerths(5);
        burgas.setName("Летище Бургас");
        burgas.setPlaces(10);
        dispatcherCallback.createAirport(burgas);
    }

    /**
     * Method which set Sofia airport marker
     */
    private void setSofiaAirport() {
        sofiaLatLng = new LatLng(42.70,23.30);
        sofiaMarker = gMap.addMarker(new MarkerOptions()
                .title("Летище София")
                .position(sofiaLatLng));
    }

    /**
     * Method which set Varna airport marker
     */
    private void setVarnaAirport() {
        varnaLatLng = new LatLng(43.20,27.90);
        varnaMarker = gMap.addMarker(new MarkerOptions()
                .title("Летище Варна")
                .position(varnaLatLng));
    }

    /**
     * Method which set Plovdiv airport marker
     */
    private void setPlovdivAirport() {
        plovdivLatLng = new LatLng(42.15,24.80);
        plovdivMarker = gMap.addMarker(new MarkerOptions()
                .title("Летище Пловдив")
                .position(plovdivLatLng));
    }

    /**
     * Method which set Burgas airport marker
     */
    private void setBurgasAirport() {
        burgasLatLng = new LatLng(42.50,27.47);
        burgasMarker = gMap.addMarker(new MarkerOptions()
                .title("Летище Бургас")
                .position(burgasLatLng));
    }

    /**
     * Method which get passed data to the fragment and checks which airports markers to create
     */
    private void getPassedData() {
        Bundle bundle = getArguments();
        fromOrToAirport = bundle.getString("fromOrToAirport");
        fromAirport = bundle.getString("fromAirport");
        if(fromAirport != null) {
            if(fromAirport.equals("Летище София")) {
                setVarnaAirport();
                setPlovdivAirport();
                setBurgasAirport();
            } else if(fromAirport.equals("Летище Варна")) {
                setSofiaAirport();
                setPlovdivAirport();
                setBurgasAirport();
            } else if(fromAirport.equals("Летище Пловдив")) {
                setSofiaAirport();
                setVarnaAirport();
                setBurgasAirport();
            } else if(fromAirport.equals("Летище Бургас")) {
                setSofiaAirport();
                setVarnaAirport();
                setPlovdivAirport();
            }
        } else {
            setSofiaAirport();
            setVarnaAirport();
            setPlovdivAirport();
            setBurgasAirport();
        }
    }

    @Override
    public void onResume() {
        gMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
    }

    /**
     * Method which set when the title of the marker is clicked. Load CreateFlightFragment and pass
     * data to it
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Bundle bundle = new Bundle();
        if(fromOrToAirport.equals("fromAirport")) {
            if (marker.equals(sofiaMarker)) {
                fromAirport = "Летище София";
            } else if (marker.equals(varnaMarker)) {
                fromAirport = "Летище Варна";
            } else if (marker.equals(plovdivMarker)) {
                fromAirport = "Летище Пловдив";
            } else if (marker.equals(burgasMarker)) {
                fromAirport = "Летище Бургас";
            }
        } else {
            if (marker.equals(sofiaMarker)) {
                toAirport = "Летище София";
            } else if (marker.equals(varnaMarker)) {
                toAirport = "Летище Варна";
            } else if (marker.equals(plovdivMarker)) {
                toAirport = "Летище Пловдив";
            } else if (marker.equals(burgasMarker)) {
                toAirport = "Летище Бургас";
            }
        }
        bundle.putString("fromAirport", fromAirport);
        bundle.putString("toAirport", toAirport);
        dispatcherCallback.loadCreateFlightFragment(bundle);
    }
}
