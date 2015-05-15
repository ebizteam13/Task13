package com.example.jiayi.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ItemFragment.OnFragmentInteractionListener, View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private EditText to1,from1;
    private Spinner arriveOrDepart;
    public Calendar c=Calendar.getInstance();

    public FavoriteDB getFavoriteDB() {
        return favoriteDB;
    }

    private FavoriteDB favoriteDB;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        timePicker = (TimePicker) findViewById(R.id.timePicker);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new MapFragment())
                .commit();
        //setUpMapIfNeeded();
        favoriteDB = new FavoriteDB(this);
    }
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MapFragment())
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ItemFragment.newInstance("a", "b"))
                        .commit();
                break;

        }
    }

    // Jing part
    public void showDialog(View view) {
        DialogHandler dialogHandler = new DialogHandler();
        dialogHandler.show(getSupportFragmentManager(), "time_picker");

        Log.e("after tim ", c.getTime().toString());
        //~~~~~~~~


    }

    public void addBookmark(View v){
        favoriteDB.addFavorite(new FavoriteItem(((EditText) findViewById(R.id.to)).getText().toString(), ((EditText) findViewById(R.id.from)).getText().toString()));
        ((Button)findViewById(R.id.addBookmark)).setEnabled(false);
    }

    public void searchRoute(){
        Intent intent = new Intent(this, showtime.class);
        to1 = (EditText) findViewById(R.id.to);
        from1 = (EditText) findViewById(R.id.from);
        arriveOrDepart = (Spinner) findViewById(R.id.spinner);
        EditText hh=(EditText) findViewById(R.id.editText);
        EditText mm=(EditText) findViewById(R.id.editText2);
        c.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hh.getText().toString()));
        c.set(Calendar.MINUTE,Integer.parseInt(mm.getText().toString()));

        String timeFormat = "hh:mm a";
        SimpleDateFormat stf = new SimpleDateFormat((timeFormat));
        String time = stf.format(c.getTime());
        // Or

        String leaveOrArrive = arriveOrDepart.getSelectedItem().toString();

        String to = to1.getText().toString();
        String from = from1.getText().toString();
        String t=String.valueOf(c.getTimeInMillis()/1000);
        Log.e("time from main ", t + to + "" + from);
        Bundle b=new Bundle();
        b.putString("time", t);
//        b.putString("");
        intent.putExtras(b);

        intent.putExtra("origin", from);
        intent.putExtra("destination",to);
        this.startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        // timePicker = (Button) findViewById(R.id.time);

        switch (v.getId()){
            case R.id.search:
                // startActivities(new Intent(this, ));
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onFragmentInteraction(String id){

    }

    @Override
    public void onFragmentInteraction(FavoriteItem favoriteItem) {

    }
}
