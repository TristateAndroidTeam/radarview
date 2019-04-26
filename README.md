# radarview

Radar view is the custom library or custom class to help people create radar based on their lat-long and center point

## Features 

You can add a custom view to each and every radar marker or object, also you can add custom center view 
You can use all the common property of constraint layout in this layout like background or etc.,

### Prerequisites

You can implement this directly download this module from GitHub or you can add a dependency from jetpack like bellow 

First, add jitpack dependancy in your project level gradle file 
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
After that add bellow dependancy in your app level gradle 
```
    implementation 'com.github.TristateAndroidTeam:radarview:1.0' //Library Radarview

```

### Example

First, add view where ever you want to create radar

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/mImgRadarBack"
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:layout_centerInParent="true"
        android:background="@drawable/ic_radar" />

    <com.tristate.radarview.RadarViewC
        android:id="@+id/mRadarCustom"
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:layout_centerInParent="true"/>

</RelativeLayout>
```

Now code part

```
mRadarCustom=(RadarViewC)findViewById(R.id.mRadarCustom);

ArrayList<ObjectModel> mDataSet = new ArrayList<>();

//Add custom data with a view, you can also add this view by looping
mDataSet.add(new ObjectModel(23.070390, 72.519176, 200, view1));
mDataSet.add(new ObjectModel(23.071559, 72.516494, 150, view2));
mDataSet.add(new ObjectModel(23.069906, 72.515504, 150, view3));


//Finally set data to view
mRadarCustom.setupData(250, mDataSet, latLongCs, mCenterView); //Here 250 is the radar radious you can set as per your choice or set 


//You can get callback of your view click 
mRadarCustom.setUpCallBack(new RadarViewC.IRadarCallBack() {
            @Override
            public void onViewClick(Object objectModel, View view) {

            }
        });

```
![alt text](https://github.com/TristateAndroidTeam/radarview/blob/master/picture/img2.png){:height="700px" width="400px"}
