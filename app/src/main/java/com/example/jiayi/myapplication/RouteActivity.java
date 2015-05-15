package com.example.jiayi.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jiayi.myapplication.DrawRoute;
import com.example.jiayi.myapplication.R;
import com.example.jiayi.myapplication.SetUpAlarm;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    ArrayList<LatLng> markerPoints;
    static String lat1, long1;
    static String lat2, long2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #map} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        /*for drawing the route on the map*/

        //instantiate the object by passing the map instance
        DrawRoute dr=new DrawRoute(map);
        //this source & destination values are used for drawing the route on google maps
       /* String source=getIntent().getExtras().getString("source");
        String destination=getIntent().getExtras().getString("destination");

        Log.d("source:",source);
        Log.d("dest",destination);
*/


     /*  LatLng source=new LatLng(40.442493,-79.942553);
        LatLng destination=new LatLng(40.444353,-79.960835);
*/

        LatLng source=new LatLng(Double.parseDouble(lat1),Double.parseDouble(long1));
        LatLng destination=new LatLng(Double.parseDouble(lat2),Double.parseDouble(long2));
       // LatLng destination=new LatLng(40.444353,-79.960835);

        dr.setUpMapAndRequestRouter(source,destination);

/*

        *//* for setting up the alarm*//*

        // pass the current context to this class for scheduling the alarm
        SetUpAlarm alarm=new SetUpAlarm(RouteActivity.this);
        //pass the number of minutes after which the alarm should go on (this should be returned from the google transit api)
        alarm.set(1);*/

    }



}
