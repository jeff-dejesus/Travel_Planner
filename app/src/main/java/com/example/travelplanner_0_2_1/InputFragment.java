package com.example.travelplanner_0_2_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class InputFragment extends Fragment implements View.OnClickListener {


    private NavController navController;
    private Button goToNext;
    private TextView userBudget;

    private AutocompleteSupportFragment getHomeAddress;
    private Fragment addressFragment;
    private String address;

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

        getHomeAddress = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.get_home_address);

        //bounds it to specific area
        getHomeAddress.setCountries("US");
        getHomeAddress.setLocationBias(
                RectangularBounds.newInstance(
                    new LatLng(38.5607528 - 0.075, -121.4342785 + 0.075),
                    new LatLng(38.5607528 + 0.075, -121.4342785 + 0.075)
                )
        );

        //set what data types about each place we want to return
        getHomeAddress.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG, Place.Field.ADDRESS));

        //get the address that the user selescts
        getHomeAddress.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
               address = place.getName();

               //passes address data to addressFragment through fragment manager
               Bundle result = new Bundle();
               result.putString("addressName", address);
               result.putString("addressLoc", place.getAddress());
               result.putDouble("lat", place.getLatLng().latitude);
               result.putDouble("lng", place.getLatLng().longitude);
               getChildFragmentManager().setFragmentResult("homeAddress", result);

               goToNext.setText(R.string.go);
            }

            @Override
            public void onError(@NotNull Status status) {
                //create error message here
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inputBudget:
                if (validateAddress(userBudget.getText().toString()))
                    goToNext.setText(R.string.go);
                else
                    goToNext.setText(R.string.skip);
                break;
            case R.id.goToNext:
                //create the navigation action
                InputFragmentDirections.ActionInputFragmentToFragmentMenu action = InputFragmentDirections.actionInputFragmentToFragmentMenu();

                action.setUserLocation(address);

                String budget = userBudget.getText().toString();
                if(!budget.matches(""))
                    action.setUserBudget(Integer.parseInt(budget));
                else
                    action.setUserBudget(-1);

                //navigate to the destination fragment
                navController.navigate(action);
                break;
        }
    }

    //todo: validate the address the user inputs into the program
    //input: string address submitted by the user.
    //Output: true if address can be found on google maps
    public boolean validateAddress(String address) {
        //put code to verify address here

        return address.equalsIgnoreCase("123");
    }
}