package com.tristate.radarsample;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tristate.radarview.LatLongCs;
import com.tristate.radarview.ObjectModel;
import com.tristate.radarview.RadarViewC;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ObjectModel> mDataSet = new ArrayList<>();
    private LatLongCs mLatCenter = new LatLongCs(23.0663701, 72.5295074);
    RadarViewC mRadarCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadarCustom=(RadarViewC)findViewById(R.id.mRadarCustom);
        animateRadar();
        loadTempData();

    }

    private void animateRadar() {
        ImageView mImgRadarBack = findViewById(R.id.mImgRadarBack);
        Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        rotation.setFillAfter(true);
        mImgRadarBack.startAnimation(rotation);
    }

    private void loadTempData() {
        mDataSet.clear();
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mCenterView = inflater.inflate(R.layout.layout_center, null);
        TextView textView = mCenterView.findViewById(R.id.mTVText);
        LatLongCs latLongCs = new LatLongCs(23.070301, 72.517406);


        TextView t1 = new TextView(this);
        t1.setText("B");

        TextView t2 = new TextView(this);
        t2.setText("B");

        TextView t3 = new TextView(this);
        t3.setText("C");

        TextView t4 = new TextView(this);
        t4.setText("D");

        TextView t5 = new TextView(this);
        t4.setText("E");


        t1.setTextColor(Color.RED);
        t2.setTextColor(Color.BLUE);
        t3.setTextColor(Color.RED);
        t4.setTextColor(Color.RED);
        t5.setTextColor(Color.BLUE);

        mDataSet.add(new ObjectModel(23.070390, 72.519176, 200, t1));
        mDataSet.add(new ObjectModel(23.071559, 72.516494, 150, t2));
        mDataSet.add(new ObjectModel(23.069906, 72.515504, 150, t3));
        mDataSet.add(new ObjectModel(23.069608, 72.516477, 150, t4));
        mDataSet.add(new ObjectModel(23.069213, 72.517739, 100, t5));
        mRadarCustom.setupData(250, mDataSet, latLongCs, mCenterView);

        mRadarCustom.setUpCallBack(new RadarViewC.IRadarCallBack() {
            @Override
            public void onViewClick(Object objectModel, View view) {

            }
        });

    }
}
