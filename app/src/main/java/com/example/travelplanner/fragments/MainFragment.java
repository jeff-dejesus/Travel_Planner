package com.example.travelplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.travelplanner.R;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment implements View.OnClickListener {

    private NavController navController;
    private Button startProgram;
    private Button viewUserAgreement;
    private CheckBox userAgreement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState){
       super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        startProgram = view.findViewById(R.id.startProgram);
        startProgram.setOnClickListener(this);

        userAgreement = view.findViewById(R.id.userAgreement);
        userAgreement.setChecked(false);
        userAgreement.setOnClickListener(this);

        viewUserAgreement = view.findViewById(R.id.viewUserAgreement);
        viewUserAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //implement cases
            case R.id.userAgreement:
                startProgram.setEnabled(((CheckBox) view).isChecked());
                break;
            case R.id.startProgram:
                navController.navigate(R.id.action_mainFragment_to_inputFragment);
                break;
            case R.id.viewUserAgreement:

                //todo: create popup window to display user agreement here
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(startProgram != null && userAgreement.isChecked()){
            startProgram.setEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}