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

//todo: add new google maps fragment that will display the location given that is a valid address. Default display is to display Sac State's address
//todo: create google search bar that will verify the location. tutorial at https://www.youtube.com/watch?v=MWowf5SkiOE
//another helpful tutorial https://stackoverflow.com/questions/45107806/autocomplete-search-bar-in-google-maps
public class InputFragment extends Fragment implements View.OnClickListener {

    /*NOTE:
    E/Google Maps Android API: Authorization failure.  Please see https://developers.google.com/maps/documentation/android-api/start for how to correctly set up the map.
    E/Google Maps Android API: In the Google Developer Console (https://console.developers.google.com)
        Ensure that the "Google Maps Android API v2" is enabled.
        Ensure that the following Android Key exists:
    	    API Key: AIzaSyC3yr4dSSh5ajV_d8qdRmn5S-ZFVtTX04I
    	    Android Application (<cert_fingerprint>;<package_name>): 4A:97:36:71:3A:F6:9A:94:59:54:3B:B1:63:BE:97:1E:9C:D2:CB:29;com.example.travelplanner_0_2_0

     */

    private NavController navController;
    private Button goToNext;
    private TextView userBudget;

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