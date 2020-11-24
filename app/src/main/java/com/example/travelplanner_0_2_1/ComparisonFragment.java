package com.example.travelplanner_0_2_1;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travelplanner_0_2_1.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.jetbrains.annotations.NotNull;

//Todo: create UI for comparison
// the bar graph relies on a dependency the wiki is available here:
//https://github.com/jjoe64/GraphView/wiki
public class ComparisonFragment extends Fragment implements View.OnClickListener {

    private NavController navController;
    private GraphView graph;
    private Button comparisonToPlanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comparison, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        graph = (GraphView) view.findViewById(R.id.comparisonGraph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                //new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        series.setSpacing(50);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        comparisonToPlanner = view.findViewById(R.id.comparisonToPlanner);
        comparisonToPlanner.setOnClickListener(this);

        navController = Navigation.findNavController(view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comparisonToPlanner:
                navController.navigate(R.id.action_comparisonFragment_to_travelPlannerFragment);
                break;

        }
    }
}