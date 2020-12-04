package com.example.travelplanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travelplanner.R;

import org.jetbrains.annotations.NotNull;

//read through this file to get a good understanding of how the Navigation with fragments work
public class MenuFragment extends Fragment implements View.OnClickListener {

    /*
     * NavController is the object that we use to control the navigation between fragments
     * we call its navController.navigate(action) in order to move the screen to show a different
     * fragment.
     *
     * the action paramenter is detailed below and what it does
     */
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
    private VehicleButtonFragment displayCar1;

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

        //here we recieve the arguements passed from navigation they are stored in this object
        MenuFragmentArgs args = MenuFragmentArgs.fromBundle(getArguments());

        TextView menuInfo = view.findViewById(R.id.menuInfo);

        //store the variables inside the menu for when the user goes back in the app
        userLocation = args.getUserLocation();
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
        // the action is an instance of a generated class. the navigation generates the code
        // all we have to do is reference the right Action class. All action objects are located in
        // different generated classes. the class name is "(Class name) + Directions"
        // then the specific direction is that .ActionFragmentXtoFragmentY we change the second part 
        // (this refers to specific arrows on navigation/nav_graph.xml) 
        // of the action type to navigate to different actions.
        // The action object specifies the direction and parameters. See navigation/nav_graph.xml
        // 
        MenuFragmentDirections.ActionFragmentMenuToDisplayDataFragment action;
        switch (v.getId()) {
            case R.id.menuToCompare:
                navController.navigate(R.id.action_fragmentMenu_to_comparisonFragment);
                break;
            case R.id.menuToPlanner:
                navController.navigate(R.id.action_fragmentMenu_to_travelPlannerFragment);
                break;
            case R.id.displayCar:
                //this first line inititalizes the action. Class is same as declaration
                action = MenuFragmentDirections.actionFragmentMenuToDisplayDataFragment();

                //we pass the parameters to the action just how setVar(var) would work on objects
                //each parameter is "set + (parameterName)"
                action.setVehicleType("car");
                action.setUserBudget(userBudget);
                action.setUserLocation(userLocation);
                //then we call the navController to move
                navController.navigate(action);
                break;
            case R.id.displayMotorCycle:
                action = MenuFragmentDirections.actionFragmentMenuToDisplayDataFragment();
                action.setVehicleType("motorcycle");
                action.setUserBudget(userBudget);
                action.setUserLocation(userLocation);
                navController.navigate(action);
                break;
            case R.id.displayTransit:
                action = MenuFragmentDirections.actionFragmentMenuToDisplayDataFragment();
                action.setVehicleType("transit");
                action.setUserBudget(userBudget);
                action.setUserLocation(userLocation);
                navController.navigate(action);
                break;
            case R.id.displayBike:
                action = MenuFragmentDirections.actionFragmentMenuToDisplayDataFragment();
                action.setVehicleType("bike");
                action.setUserBudget(userBudget);
                action.setUserLocation(userLocation);
                navController.navigate(action);
                break;
            case R.id.displayWalk:
                action = MenuFragmentDirections.actionFragmentMenuToDisplayDataFragment();
                action.setVehicleType("walk");
                action.setUserBudget(userBudget);
                action.setUserLocation(userLocation);
                navController.navigate(action);
                break;
        }
    }
}
