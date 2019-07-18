package com.simanisandroid.simanis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
//import com.google.firebase.database.ValueEventListener;
//import com.jjoe64.graphview.DefaultLabelFormatter;
//import com.jjoe64.graphview.GraphView;
//import com.jjoe64.graphview.series.DataPoint;
//import com.jjoe64.graphview.series.DataPointInterface;
//import com.jjoe64.graphview.series.LineGraphSeries;
//import com.jjoe64.graphview.series.OnDataPointTapListener;
//import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GrafikFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference reference;
    GraphView graphView;
    LineGraphSeries series;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM \nHH:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grafik, container, false);

        series = new LineGraphSeries();
        graphView = v.findViewById(R.id.grafik);
        graphView.addSeries(series);

        series.setAnimated(true);
        series.setThickness(6);
        series.setDrawBackground(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                String msg="Tanggal : "+dateFormat.format(new Date((long)dataPoint.getX())) +"\nTetesan Per Menit : "+dataPoint.getY();
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Ruangan/1/1/Grafik");

        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Grafik");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for (DataSnapshot mydataSnapshot:dataSnapshot.getChildren()){
                    PointValue pointValue = mydataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getTgl(), pointValue.getTetesan());
                    index++;
                }
                series.resetData(dp);
                zoom(graphView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void zoom(GraphView graphView) {
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return dateFormat.format(new Date((long) value));
                }else {
                    return super.formatLabel(value,isValueX);
                }

            }
        });
    }
}
