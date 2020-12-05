package com.example.travelplanner.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travelplanner.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class InputFragment extends Fragment implements View.OnClickListener, PlaceSelectionListener {


    private NavController navController;
    private Button goToNext;
    private TextView userBudget;

    private AutocompleteSupportFragment getHomeAddress;
    private Fragment addressFragment;
    private String address;
    private LatLng addressLatLng;

    private final double BIAS_RANGE = 0.075;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        goToNext = view.findViewById(R.id.goToNext);
        goToNext.setOnClickListener(this);

        userBudget = view.findViewById(R.id.inputBudget);
        userBudget.setOnClickListener(this);

        addressFragment = getChildFragmentManager().findFragmentById(R.id.display_address);

        String api_key = getString(R.string.google_maps_key);
        if (!Places.isInitialized())
            Places.initialize(getActivity().getApplicationContext(), api_key);

        //Sets up the AutocompleteSupportFragment
        getHomeAddress = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.get_home_address);

        //bounds it to specific area
        getHomeAddress.setCountries("US");
        getHomeAddress.setLocationBias(
                RectangularBounds.newInstance(
                        new LatLng(AddressFragment.SAC_STATE_LOC.latitude - BIAS_RANGE, AddressFragment.SAC_STATE_LOC.longitude - BIAS_RANGE),
                        new LatLng(AddressFragment.SAC_STATE_LOC.latitude + BIAS_RANGE, AddressFragment.SAC_STATE_LOC.longitude + BIAS_RANGE)
                )
        );

        //set what data types about each place we want to return
        getHomeAddress.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        //get the address that the user selects
        getHomeAddress.setOnPlaceSelectedListener(this);
        getHomeAddress.getView().findViewById(R.id.places_autocomplete_clear_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goToNext:
                InputFragmentDirections.ActionInputFragmentToFragmentMenu action;
                action = InputFragmentDirections.actionInputFragmentToFragmentMenu();

                String budget = userBudget.getText().toString();

                if (budget.matches(""))
                    budget = "-1";
                action.setUserBudget(Integer.parseInt(budget));

                action.setUserLocation(address);
                action.setCoordinates(addressLatLng);

                navController.navigate(action);
                break;
            case R.id.places_autocomplete_clear_button:
                getHomeAddress.setText(null);
                goToNext.setText(R.string.skip);
                address = "";
                addressLatLng = null;
                //tells the google map to clear the map
                getChildFragmentManager().setFragmentResult("clearMap", null);
        }
    }

    //listener for when the user selects an address from autocompletefragment
    @Override
    public void onPlaceSelected(@NonNull Place place) {
        //store address
        address = place.getAddress();
        addressLatLng = place.getLatLng();

        //passes address data to addressFragment through fragment manager
        Bundle result = new Bundle();
        result.putString("addressName", place.getName());
        result.putString("addressLoc", address);
        result.putDouble("lat", addressLatLng.latitude);
        result.putDouble("lng", addressLatLng.longitude);
        //sends address to map to update the map
        getChildFragmentManager().setFragmentResult("homeAddress", result);

        goToNext.setText(R.string.go);

    }

    @Override
    public void onError(@NonNull Status status) {

    }
}