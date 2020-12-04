package com.example.travelplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travelplanner.R;

import org.jetbrains.annotations.NotNull;

//todo: create UI for Travel planner
public class TravelPlannerFragment extends Fragment implements View.OnClickListener {

    private NavController navController;
    private Button plannerToComparison;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_planner, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        plannerToComparison = view.findViewById(R.id.plannerToComparison);
        plannerToComparison.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.plannerToComparison:
                navController.navigate(R.id.action_travelPlannerFragment_to_comparisonFragment);
                break;

        }
    }
}