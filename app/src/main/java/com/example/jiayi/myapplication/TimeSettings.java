package com.example.jiayi.myapplication;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Jing on 5/9/15.
 */
public class TimeSettings implements TimePickerDialog.OnTimeSetListener {
    Context context;

    public TimeSettings(Context context) {

        this.context = context;
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final Calendar c = Calendar.getInstance();
        Toast.makeText(context,"Selected time is " + hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
    }

}
