package com.example.travelplanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travelplanner.R;


public class DisplayDataFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private NavController navController;
    private ImageView displayVehicleImage;
    private Spinner displayVehicleTitle;
    private Button displayToComparison;
    private Button displayToPlanner;
    //TODO: add in the rest of the fields of all the UI elements in Fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_data, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DisplayDataFragmentArgs args = DisplayDataFragmentArgs.fromBundle(getArguments());

        //initialize buttons and add listener to all of them
        displayToComparison = view.findViewById(R.id.displayToComparison);
        displayToComparison.setOnClickListener(this);
        displayToPlanner = view.findViewById(R.id.displayToPlanner);
        displayToPlanner.setOnClickListener(this);

        //initialize the rest of the components to be edited by them
        displayVehicleImage = view.findViewById(R.id.displayVehicleImage);
        displayVehicleTitle = view.findViewById(R.id.displayTitleVehicle);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item, getResources().getStringArray(R.array.vehicle_array));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        displayVehicleTitle.setAdapter(adapter);
        displayVehicleTitle.setOnItemSelectedListener(this);

        //edits all the components based on what vehicle type the user clicked on
        displayData(args.getVehicleType());

        navController = Navigation.findNavController(view);
        view.findViewById(R.id.distanceFromHome).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()) {
           case R.id.displayToComparison:
               navController.navigate(R.id.action_displayDataFragment_to_comparisonFragment);
               break;
           case R.id.displayToPlanner:
               navController.navigate(R.id.action_displayDataFragment_to_travelPlannerFragment);
               break;
       }
    }

    //This is just a very long switch where we change out all the variables depending on the tab
    //TODO calculate data and display the data separate methods will be made to calculate
    // different things
    public void displayData(String vehicleType){
        switch (vehicleType){
            case"car":
            default:
                displayVehicleImage.setImageResource(R.drawable.car_image);
                break;
            case"motorcycle":
                displayVehicleImage.setImageResource(R.drawable.motorcycle_image);
                break;
            case "transit":
                displayVehicleImage.setImageResource(R.drawable.transit_image);
                break;
            case "bike":
                displayVehicleImage.setImageResource(R.drawable.bike_image);
                break;
            case"walk":
                displayVehicleImage.setImageResource(R.drawable.walking_image);
                break;

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String title = displayVehicleTitle.getSelectedItem().toString().toLowerCase();
        displayData(title);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }

    private double calculateCost(String vehicleType){
        //calculate the cost here, use local variables from the args object
        //todo: convert args to private variable for this class
        return 0;
    }
}