package com.example.jiayi.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class showtime extends Activity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private TextView stopView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);
        //setUpMapIfNeeded();
//        stopView=(TextView)findViewById(R.id.textView2);
//        button=(Button)findViewById(R.id.button);

        DirectionsFetcher df=new DirectionsFetcher();
        String origin="5440 fifth avenue";
        String destination="carnegie mellon university";
        df.execute(origin,destination);
        //TRY TO SHOW USERS CURRENT LOCATION

    }

    private class DirectionsFetcher extends AsyncTask<String, Integer, JSONArray> {
        private List<LatLng> latLngs = new ArrayList<LatLng>();
        @Override
        protected void onPreExecute(){
            Log.e("-----------------","Thread is starting now");
        }
        @Override
        protected JSONArray doInBackground(String... strs) {
            String line="";
            Log.e("--------------","in do in background method ");
            try {
                String origin=strs[0];
                String destination=strs[1];
//                URL url = new URL();
//                url.openConnection();
                String url="http://maps.googleapis.com/maps/api/directions/json?origin="+URLEncoder.encode(origin,"UTF-8")+"&destination="+URLEncoder.encode(destination,"UTF-8")+"&mode=transit"+"&alternatives=true";

                Log.e("url  ",url);
                HttpGet httpGet = new HttpGet(url);
//                httpGet.setHeader("Host","http://maps.googleapis.com");
                HttpResponse hresponse=new DefaultHttpClient().execute(httpGet);
                HttpEntity hEntity=hresponse.getEntity();
                String response = EntityUtils.toString(hEntity);
                //got the json now parse and display on the screen the required things
                JSONObject jsonObj = new JSONObject(response);
                JSONArray routes=jsonObj.getJSONArray("routes");
//                Log.e("-------------size------------- ",routes.length()+"");
                JSONObject route = routes.getJSONObject(0);
                JSONArray legs ;
                JSONObject leg ;
                JSONArray steps=null ;
                JSONObject dist;
                Integer distance ;
//                if(route.has("legs")) {
                JSONArray a=route.getJSONArray("legs");
                JSONObject as=a.getJSONObject(0);
                JSONArray hh= as.getJSONArray("steps");
                JSONObject step=hh.getJSONObject(0);

                String ss=(String)step.get("travel_mode");
                JSONObject details=step.getJSONObject("transit_details");
                JSONObject arrivalStop=details.getJSONObject("arrival_stop");
                JSONObject departureStop=details.getJSONObject("departure_stop");
                return routes;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }
        @Override
        protected void onPostExecute(JSONArray routes) {
            //parse the json here and display it on screen
            try {
                LinearLayout ll=(LinearLayout)findViewById(R.id.skandy);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                for(int i=0;i<routes.length();i++){
                    JSONObject route=routes.getJSONObject(i);
                    JSONArray a = route.getJSONArray("legs");
                    JSONObject as = a.getJSONObject(0);
                    JSONArray hh = as.getJSONArray("steps");
                    JSONObject step = hh.getJSONObject(0);

                    String ss = (String) step.get("travel_mode");
                    if(!step.has("transit_details")){continue;}

                    JSONObject details = step.getJSONObject("transit_details");

                    JSONObject arrivalStop = details.getJSONObject("arrival_stop");
                    JSONObject arrivalTime = details.getJSONObject("arrival_time");

                    JSONObject departureStop = details.getJSONObject("departure_stop");
                    JSONObject departureTime = details.getJSONObject("departure_time");

                    String busName=(String)details.getJSONObject("line").get("short_name");
                    final String arrivalT=(String)arrivalTime.get("text");
                    String arrivalS=(String)arrivalStop.get("name");
                    final String deptT=(String)departureTime.get("text");
                    String deptS=(String)departureStop.get("name");

                    Button my=new Button(showtime.this);
                    my.setText(busName+"\t"+arrivalT+" "+arrivalS+"\n\t"+deptT+" "+deptS);
                    my.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                new AlertDialog.Builder(showtime.this)
                                        .setTitle("Set Alarm")
                                        .setMessage("Do you want to set an alarm to alert  you of the destination?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                String time= null;
                                                try {
                                                    time = TWENTY_FOUR_TF.format(
                                                            TWELVE_TF.parse(arrivalT));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
//                            SimpleDateFormat currF=new SimpleDateFormat("HH:mm");
//                            Date curr=new Date();
//                            String sas=currF.format(curr);
                                                Calendar arr = Calendar.getInstance();
                                                String times[]=time.split(":");
                                                arr.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[0]));
                                                arr.set(Calendar.MINUTE,Integer.parseInt(times[1]));
                                                Calendar curr=Calendar.getInstance();
                                                long r=arr.getTime().getTime()-curr.getTime().getTime();
                                                Log.e("***************",r+"");
                                                int d=(int)(r/(1000*60));
                                                SetUpAlarm s=new SetUpAlarm(showtime.this);
                                                Toast.makeText(showtime.this, "Alarm set !!!", Toast.LENGTH_LONG).show();
                                                s.set(1);


                                                //displaying the route on the map
                                                Intent i=new Intent(showtime.this,RouteActivity.class);
                                                showtime.this.startActivity(i);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();



                        }
                    });
                    ll.addView(my);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        // Replace with KK:mma if you want 0-11 interval
        private final DateFormat TWELVE_TF = new SimpleDateFormat("hh:mma");
        // Replace with kk:mm if you want 1-24 interval
        private final DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("HH:mm");

        public String convertTo24HoursFormat(String twelveHourTime)
                throws ParseException {
            return TWENTY_FOUR_TF.format(
                    TWELVE_TF.parse(twelveHourTime));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
//    private void setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
////        Log.i("amey--------","in setting up map");
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                    .getMap();
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
//    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
