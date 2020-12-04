package com.example.travelplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelplanner.R;

import org.jetbrains.annotations.NotNull;

public class VehicleButtonFragment extends Fragment {

    private ImageView backgroundImg;
    private TextView title;
    private TextView info;
    private Button vehicleSelect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vehicle_button, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backgroundImg = view.findViewById(R.id.vehicleButtonBackground);
        title = view.findViewById(R.id.vehicleButtonTitle);
        info = view.findViewById(R.id.vehicleButtonInfo);
        vehicleSelect = view.findViewById(R.id.vehicleButtonSelect);
    }

    public void setBackgroundImg(int id){
        backgroundImg.setImageResource(id);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(String titleString) {
       title.setText(titleString);
    }

    public TextView getInfo() {
        return info;
    }

    public void setInfo(String infoString) {
        info.setText(infoString);
    }

    public Button getVehicleSelect() {
        return vehicleSelect;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        vehicleSelect.setOnClickListener(listener);
    }
}