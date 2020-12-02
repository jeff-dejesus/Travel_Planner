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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class InputFragment extends Fragment implements View.OnClickListener {


    private NavController navController;
    private Button goToNext;
    private TextView userBudget;

    private AutocompleteSupportFragment getHomeAddress;

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

        //todo: figure out how to reference the key from the xml file to avoid having a string literal
        Places.initialize(getActivity().getApplicationContext(), "AIzaSyCGAnDlG13YXNfiwZbvjt0zAbVtnVx1UdU");

        getHomeAddress = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.get_home_address);

        // Specify the types of place data to return.
        getHomeAddress.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        //specifies the location to a more precise area so search results are more accurate
        getHomeAddress.setCountries("US");

        //currently restricts to sacramento but need to figure out how to restrict to california
        getHomeAddress.setLocationRestriction(RectangularBounds.newInstance(
                new LatLng(38.5534836, -121.4494276),
                new LatLng(38.587359, -121.5082156)));

        // Set up a PlaceSelectionListener to handle the response.
        getHomeAddress.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.

            }

            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
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
                //pass the parameters into the action
                String budget = userBudget.getText().toString();
                if(!budget.matches(""))
                    action.setUserBudget(Integer.parseInt(budget));
                else
                    action.setUserBudget(-1);
                //temporary parameter passing
                //todo: get the validated address and pass it as the parameter of setUserLocation()
                action.setUserLocation("Sac State");
                //navigate to the destination fragment
                navController.navigate(action);

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