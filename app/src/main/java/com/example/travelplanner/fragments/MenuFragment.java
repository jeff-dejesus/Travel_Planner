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

    private VehicleButtonFragment display0;
    private VehicleButtonFragment display1;
    private VehicleButtonFragment display2;
    private VehicleButtonFragment display3;
    private VehicleButtonFragment display4;

    private String[] vehicleDisplayOrder = new String[5];

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

        //here we receive the arguments passed from navigation they are stored in this object
        MenuFragmentArgs args = MenuFragmentArgs.fromBundle(getArguments());

        TextView menuInfo = view.findViewById(R.id.menuInfo);

        //store the variables inside the menu for when the user goes back in the app
        userLocation = args.getUserLocation();
        userBudget = args.getUserBudget();

        navController = Navigation.findNavController(view);

        menuToCompare = view.findViewById(R.id.menuToCompare);
        menuToCompare.setOnClickListener(this);
        menuToPlanner = view.findViewById(R.id.menuToPlanner);
        menuToPlanner.setOnClickListener(this);

        display0 = (VehicleButtonFragment) getChildFragmentManager().findFragmentById(R.id.fragment0);
        display1 = (VehicleButtonFragment) getChildFragmentManager().findFragmentById(R.id.fragment1);
        display2 = (VehicleButtonFragment) getChildFragmentManager().findFragmentById(R.id.fragment2);
        display3 = (VehicleButtonFragment) getChildFragmentManager().findFragmentById(R.id.fragment3);
        display4 = (VehicleButtonFragment) getChildFragmentManager().findFragmentById(R.id.fragment4);

        //TODO: sort the buttons so modes that seem more optimal for the user are presented first

        //to sort out the order, reorder the array below, or in the create buttons, reorder which is set first
        //reminder: display 0 will always be on top and display 4 will always be the last 1 so you have to set their text
        //to a different mode of transportation in order for it to be in different order
        VehicleButtonFragment[] fragmentOrder = new VehicleButtonFragment[]{display2, display3, display1, display4, display0};
        createButtons(fragmentOrder);
    }

    private void createButtons(VehicleButtonFragment[] fragmentOrder) {
        fragmentOrder[0].setBackgroundImg(R.drawable.car_image);
        fragmentOrder[0].setTitle("Car");
        fragmentOrder[0].setInfo("click this to get info on car");
        fragmentOrder[0].setOnClickListener(this);
        vehicleDisplayOrder[0] = "car";

        fragmentOrder[1].setBackgroundImg(R.drawable.motorcycle_image);
        fragmentOrder[1].setTitle("Motorcycle");
        fragmentOrder[1].setInfo("click this to get info on motorcycle");
        fragmentOrder[1].setOnClickListener(this);
        vehicleDisplayOrder[1] = "motorcycle";

        fragmentOrder[2].setBackgroundImg(R.drawable.transit_image);
        fragmentOrder[2].setTitle("Transit");
        fragmentOrder[2].setInfo("click this to get info on Transit");
        fragmentOrder[2].setOnClickListener(this);
        vehicleDisplayOrder[2] = "transit";

        fragmentOrder[3].setBackgroundImg(R.drawable.bike_image);
        fragmentOrder[3].setTitle("Bike");
        fragmentOrder[3].setInfo("click this to get info on Bike");
        fragmentOrder[3].setOnClickListener(this);
        vehicleDisplayOrder[3] = "bike";

        fragmentOrder[4].setBackgroundImg(R.drawable.walking_image);
        fragmentOrder[4].setTitle("Walk");
        fragmentOrder[4].setInfo("click this to get info on walk");
        fragmentOrder[4].setOnClickListener(this);
        vehicleDisplayOrder[4] = "walk";
    }

    @Override
    public void onClick(View view) {
        // the action is an instance of a generated class. the navigation generates the code
        // all we have to do is reference the right Action class. All action objects are located in
        // different generated classes. the class name is "(Class name) + Directions"
        // then the specific direction is that .ActionFragmentXtoFragmentY we change the second part 
        // (this refers to specific arrows on navigation/nav_graph.xml) 
        // of the action type to navigate to different actions.
        // The action object specifies the direction and parameters. See navigation/nav_graph.xml
        // 
        MenuFragmentDirections.ActionFragmentMenuToDisplayDataFragment action;
        switch (view.getId()) {
            case R.id.menuToCompare:
                navController.navigate(R.id.action_fragmentMenu_to_comparisonFragment);
                break;
            case R.id.menuToPlanner:
                navController.navigate(R.id.action_fragmentMenu_to_travelPlannerFragment);
                break;
            case R.id.vehicleButtonSelect:
                action = MenuFragmentDirections.actionFragmentMenuToDisplayDataFragment(vehicleDisplayOrder);

                if (view == display0.getVehicleSelect())
                    action.setVehicleType(vehicleDisplayOrder[0]);
                else if (view == display1.getVehicleSelect())
                    action.setVehicleType(vehicleDisplayOrder[1]);
                else if (view == display2.getVehicleSelect())
                    action.setVehicleType(vehicleDisplayOrder[2]);
                else if (view == display3.getVehicleSelect())
                    action.setVehicleType(vehicleDisplayOrder[3]);
                else if (view == display4.getVehicleSelect())
                    action.setVehicleType(vehicleDisplayOrder[4]);

                action.setUserBudget(userBudget);
                action.setUserLocation(userLocation);
                navController.navigate(action);
                break;
        }
    }
}
