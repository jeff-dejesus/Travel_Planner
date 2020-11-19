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

import com.example.travelplanner_0_2_1.R;

import org.jetbrains.annotations.NotNull;

public class FragmentMenu extends Fragment implements View.OnClickListener {

    private NavController navController;
    private String userLocation;
    private int userBudget;
    private Button menuToCompare;
    private Button menuToPlanner;
    private Button displayCar;
    private Button displayMotorCycle;
    private Button displayTransit;
    private Button displayBike;
    private Button displayWalk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentMenuArgs args = FragmentMenuArgs.fromBundle(getArguments());

        TextView menuInfo = view.findViewById(R.id.menuInfo);

        //store the variables inside the menu for when the user goes back in the app
        userLocation= args.getUserLocation();
        userBudget = args.getUserBudget();

        navController = Navigation.findNavController(view);
        view.findViewById(R.id.displayCar).setOnClickListener(this);

        menuToCompare = view.findViewById(R.id.menuToCompare);
        menuToCompare.setOnClickListener(this);
        menuToPlanner = view.findViewById(R.id.menuToPlanner);
        menuToPlanner.setOnClickListener(this);

        //TODO: sort the buttons so modes that seem more optimal for the user are presented first

        displayCar = view.findViewById(R.id.displayCar);
        displayCar.setOnClickListener(this);
        displayMotorCycle = view.findViewById(R.id.displayMotorCycle);
        displayMotorCycle.setOnClickListener(this);
        displayTransit = view.findViewById(R.id.displayTransit);
        displayTransit.setOnClickListener(this);
        displayBike = view.findViewById(R.id.displayBike);
        displayBike.setOnClickListener(this);
        displayWalk = view.findViewById(R.id.displayWalk);
        displayWalk.setOnClickListener(this);
    }

    //todo: clean up switch so less code. can take out some cases in replace of IF statement
    @Override
    public void onClick(View v) {
        FragmentMenuDirections.ActionFragmentMenuToDisplayDataFragment action;
       switch(v.getId()){
           case R.id.menuToCompare:
               navController.navigate(R.id.action_fragmentMenu_to_comparisonFragment);
               break;
           case R.id.menuToPlanner:
               navController.navigate(R.id.action_fragmentMenu_to_travelPlannerFragment);
               break;
           case R.id.displayCar:
               action = FragmentMenuDirections.actionFragmentMenuToDisplayDataFragment();
               action.setVehicleType("car");
               action.setUserBudget(userBudget);
               action.setUserLocation(userLocation);
               navController.navigate(action);
               break;
           case R.id.displayMotorCycle:
               action = FragmentMenuDirections.actionFragmentMenuToDisplayDataFragment();
               action.setVehicleType("motorcycle");
               action.setUserBudget(userBudget);
               action.setUserLocation(userLocation);
               navController.navigate(action);
               break;
           case R.id.displayTransit:
                action = FragmentMenuDirections.actionFragmentMenuToDisplayDataFragment();
               action.setVehicleType("transit");
               action.setUserBudget(userBudget);
               action.setUserLocation(userLocation);
               navController.navigate(action);
               break;
           case R.id.displayBike:
                action = FragmentMenuDirections.actionFragmentMenuToDisplayDataFragment();
               action.setVehicleType("bike");
               action.setUserBudget(userBudget);
               action.setUserLocation(userLocation);
               navController.navigate(action);
               break;
           case R.id.displayWalk:
                action = FragmentMenuDirections.actionFragmentMenuToDisplayDataFragment();
               action.setVehicleType("walk");
               action.setUserBudget(userBudget);
               action.setUserLocation(userLocation);
               navController.navigate(action);
               break;
       }
    }
}