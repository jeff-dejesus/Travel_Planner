package com.example.travelplanner_0_2_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelplanner_0_2_1.animation.LatLngInterpolator;
import com.example.travelplanner_0_2_1.animation.MarkerAnimation;
import com.example.travelplanner_0_2_1.directionhelpers.FetchUrl;
import com.example.travelplanner_0_2_1.directionhelpers.TaskLoadedCallback;
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

    private MapView addressMap;
    private GoogleMap googleMap;
    public static final LatLng SAC_STATE_LOC = new LatLng(38.5575016, -121.4276552);
    private Marker homeMarker, sacStateMarker;
    private Polyline directions;

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

        getParentFragmentManager().setFragmentResultListener("homeAddress", this, this);
    }

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

    @Override
    public void onTaskDone(Object... values) {
        directions = googleMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull final Bundle result) {
        //create new callback that will add marker
        addressMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                final LatLng homePos = new LatLng(result.getDouble("lat"), result.getDouble("lng"));
                updateCamera(googleMap, homePos);

                if (homeMarker == null) {
                    homeMarker = googleMap.addMarker(new MarkerOptions().position(homePos).title(result.getString("addressLoc")));
                    createDirections();
                } else {
                    if (directions != null) {
                        directions.remove();
                    }
                    homeMarker.setTitle(result.getString("addressLoc"));
                    MarkerAnimation.animateMarkerToGB(homeMarker, homePos, new LatLngInterpolator.Linear());
                    //handler waits for marker to finish moving in order to update the path from the marker
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            createDirections(); // create directions again from new home
                        }
                    }, MarkerAnimation.ANIMATION_DURATION);
                }
            }
        });
    }

    private void createDirections() {
        new FetchUrl(AddressFragment.this).execute(getUrl(homeMarker.getPosition(), SAC_STATE_LOC, "driving"), "driving");
    }

    private void updateCamera(GoogleMap googleMap, LatLng homeLoc) {
        LatLngBounds.Builder viewBuilder = new LatLngBounds.Builder();
        viewBuilder.include(SAC_STATE_LOC);
        viewBuilder.include(homeLoc);
        LatLngBounds bounds = viewBuilder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        googleMap.animateCamera(cameraUpdate);
    }

    //used to create the url to be used to get the directions
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
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