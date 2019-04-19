package com.tristate.radarview;

import android.view.View;

public class ObjectModel {
    private double mLatitude;
    private double mLongitude;
    private double mDistance;
    private View mView;


    public ObjectModel(double mLatitude, double mLongitude, double mDistance, View mView) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mDistance = mDistance;
        this.mView = mView;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmDistance() {
        return mDistance;
    }

    public void setmDistance(double mDistance) {
        this.mDistance = mDistance;
    }


}
