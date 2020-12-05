package com.example.travelplanner.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelplanner.R;
import com.example.travelplanner.animation.LatLngInterpolator;
import com.example.travelplanner.animation.MarkerAnimation;
import com.example.travelplanner.directionhelpers.FetchUrl;
import com.example.travelplanner.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class AddressFragment extends Fragment implements OnMapReadyCallback, TaskLoadedCallback, FragmentResultListener {

    /*Google maps fragment that is used for displaying the initial route from the users home to Sac State within the InputFragment.java*/
    private MapView addressMap;
    private GoogleMap googleMap;
    public static final LatLng SAC_STATE_LOC = new LatLng(38.5575016, -121.4276552);
    private Marker homeMarker, sacStateMarker;
    private Polyline directions;

    //initialize map
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up the listeners to listen for when actions want to be sent from Input fragment to this fragment
        getParentFragmentManager().setFragmentResultListener("homeAddress", this, this);
        getParentFragmentManager().setFragmentResultListener("clearMap", this, this);
    }

    //method that creates the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // create marker at location
        googleMap.addMarker(new MarkerOptions()
                .position(SAC_STATE_LOC)
                .title("Sacramento State")
        );

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(SAC_STATE_LOC)
                .zoom(10)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setMapToolbarEnabled(false);
    }

    //Thread will call this to add the polyline to the map
    @Override
    public void onTaskDone(Object... values) {
        directions = googleMap.addPolyline((PolylineOptions) values[0]);
    }

    //Listener for when the Parent fragment (Inputfragment) sends data to this fragment
    //this does not use the navigation component because we aren't moving to and from the fragment
    @Override
    public void onFragmentResult(@NonNull final String requestKey, @NonNull final Bundle result) {
        //create new callback that will update the map
        addressMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                //if the request is to create/update marker this method will create the marker and
                // draw the path needed
                if (requestKey.equals("homeAddress")) {
                    final LatLng homePos = new LatLng(result.getDouble("lat"), result.getDouble("lng"));
                    updateCamera(googleMap, homePos);

                    if (homeMarker == null) {
                        //if no marker
                        homeMarker = googleMap.addMarker(new MarkerOptions().position(homePos).title(result.getString("addressLoc")));
                        createDirections();
                    } else {
                        //if there is marker
                        if (directions != null) {
                            directions.remove(); //clear previous directions
                        }
                        //animates the marker to move from its previous spot to new spot
                        homeMarker.setTitle(result.getString("addressLoc"));
                        MarkerAnimation.animateMarkerToGB(homeMarker, homePos, new LatLngInterpolator.Linear());
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            //create thread that waits for marker to finish moving in order to update
                            //directions
                            public void run() {
                                createDirections();
                            }
                        }, MarkerAnimation.ANIMATION_DURATION);
                    }
                } else{
                    //if the request is to clear the search field
                    if(homeMarker!= null) {
                        homeMarker.remove();
                        homeMarker = null;
                    }
                    if (directions != null)
                        directions.remove();
                }
            }
        });
    }

    //method that is called to create the directions from the home marker to Sac state marker
    private void createDirections() {
        new FetchUrl(AddressFragment.this).execute(getUrl(homeMarker.getPosition(), SAC_STATE_LOC, "driving"), "driving");
    }

    //used to create the url to be used to get the directions
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?"
                + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    //readjusts the camera so that both markers are visible
    private void updateCamera(GoogleMap googleMap, LatLng homeLoc) {
        LatLngBounds.Builder viewBuilder = new LatLngBounds.Builder();
        viewBuilder.include(SAC_STATE_LOC);
        viewBuilder.include(homeLoc);
        LatLngBounds bounds = viewBuilder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        googleMap.animateCamera(cameraUpdate);
    }

    public void clearMap() {
        addressMap.getMapAsync(this);
    }

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