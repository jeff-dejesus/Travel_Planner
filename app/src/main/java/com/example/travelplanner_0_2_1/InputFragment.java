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
public class InputFragment extends Fragment implements View.OnClickListener {

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