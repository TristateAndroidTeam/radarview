package com.tristate.radarview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

public class RadarViewC extends ConstraintLayout {
    private LatLongCs mLatLongCenter = null;
    private Context mContext;
    private double mRadarDistance;
    private ArrayList<ObjectModel> mDataSets;
    int mWidthHeight = 0;
    private View mCenterView;
    RadarViewC mLayout;
    int idCenterLayout = 5001;
    private IRadarCallBack radarCallBack;

    public void setupData(double mRadarDistance, ArrayList<ObjectModel> mDataSets, LatLongCs mLatLongCenter, View centerView) {
        this.mRadarDistance = mRadarDistance;
        this.mDataSets = mDataSets;
        this.mLatLongCenter = mLatLongCenter;
        this.mCenterView = centerView;
        startDrawRadar();
    }

    public RadarViewC(Context context) {
        super(context);
        this.mContext = context;
        mLayout = this;
    }

    public RadarViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mLayout = this;
    }

    public RadarViewC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mLayout = this;
    }

    private void startDrawRadar() {
        if (mContext == null || mRadarDistance <= 0
                || mDataSets == null) {
            return;
        }
        ViewTreeObserver viewTreeObserver = mLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = mLayout.getMeasuredWidth();
                int height = mLayout.getMeasuredHeight();
                mWidthHeight = width > height ? height : width;
                setupDataOnRadar();
            }
        });

    }

    private void setupDataOnRadar() {
        mLayout.removeAllViews();

        ConstraintLayout.LayoutParams clpcontactUs = new LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        mCenterView.setId(idCenterLayout);

        mCenterView.setLayoutParams(clpcontactUs);
        mLayout.addView(mCenterView);

        ConstraintSet mCenterSet = new ConstraintSet();
        mCenterSet.clone(mLayout);

        mCenterSet.connect(mCenterView.getId(), ConstraintSet.START, mLayout.getId(), ConstraintSet.START);
        mCenterSet.connect(mCenterView.getId(), ConstraintSet.END, mLayout.getId(), ConstraintSet.END);
        mCenterSet.connect(mCenterView.getId(), ConstraintSet.TOP, mLayout.getId(), ConstraintSet.TOP);
        mCenterSet.connect(mCenterView.getId(), ConstraintSet.BOTTOM, mLayout.getId(), ConstraintSet.BOTTOM);
        mCenterSet.applyTo(mLayout);


        for (int position = 0; position < mDataSets.size(); position++) {
            View viewD = mDataSets.get(position).getmView();
            viewD.setId(position);
            mLayout.addView(viewD);
            final int finalPosition = position;
            viewD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radarCallBack != null)
                        radarCallBack.onViewClick(mDataSets.get(finalPosition), mDataSets.get(finalPosition).getmView());
                }
            });
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(mLayout);

            int radious = findRadious(mLatLongCenter, mDataSets.get(position).getmLatitude(), mDataSets.get(position).getmLongitude());

            constraintSet.constrainCircle(viewD.getId(), mCenterView.getId(), radious, getAngleFromLatLong(mLatLongCenter, mDataSets.get(position).getmLatitude(), mDataSets.get(position).getmLongitude()));
            constraintSet.applyTo(mLayout);
        }
    }


    /**
     * Method for get angle of second point from first point
     *
     * @param mLatLongCenter Object for center point latlong
     * @param lat2           Second latitude
     * @param lng2           Second longitude
     * @return Angle which find using tow location point
     */
    private float getAngleFromLatLong(LatLongCs mLatLongCenter, double lat2, double lng2) {
        double lat1 = mLatLongCenter.latitude;
        double lng1 = mLatLongCenter.longitude;

        double dLon = (lng2 - lng1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double brng = Math.toDegrees((Math.atan2(y, x)));
        brng = (360 - ((brng + 360) % 360));

        return (float) brng;
    }


    /**
     * Method for find radious between tow lat long
     *
     * @param mLatLongCenter Object for center point latlong
     * @param lat2           Second latitude
     * @param lng2           Second longitude
     * @return radios which calculated using tow location point
     */
    private int findRadious(LatLongCs mLatLongCenter, double lat2, double lng2) {
        double radiousView = mWidthHeight / 2d;
        double percent = (distance(mLatLongCenter.latitude, lat2, mLatLongCenter.longitude, lng2, 0, 0) * 100) / mRadarDistance;
        double radius = (radiousView * percent) / 100;
        return (int) radius;
    }


    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public void setUpCallBack(IRadarCallBack radarCallBack) {
        this.radarCallBack = radarCallBack;
    }

    public interface IRadarCallBack {
        void onViewClick(Object objectModel, View view);
    }
}
