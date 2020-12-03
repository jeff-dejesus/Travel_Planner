package com.example.travelplanner_0_2_1;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class AddressFragment extends Fragment implements OnMapReadyCallback{

    MapView addressMap;
    private GoogleMap googleMap;
    private Marker marker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container,
                false);
        addressMap = (MapView) view.findViewById(R.id.mapView);
        addressMap.onCreate(savedInstanceState);

        addressMap.onResume(); // needed to get the map to display immediately

        MapsInitializer.initialize(getActivity().getApplicationContext());

        addressMap.getMapAsync(this);
        return view;
    }

    public void setHomeMarker(){

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // create marker at location
        LatLng sacState = new LatLng(38.5607528, -121.4342785);
        googleMap.addMarker(new MarkerOptions()
                .position(sacState)
                .title("Sacramento State")
        );

        CameraPosition cameraPosition = new CameraPosition.Builder().target(sacState).zoom(10).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setMapToolbarEnabled(false);
    }

    //https://developers.google.com/maps/documentation/android-sdk/start

    @Override
    public void onResume() {
        super.onResume();
        addressMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        addressMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addressMap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        addressMap.onLowMemory();
    }
}