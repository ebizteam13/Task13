package com.example.jiayi.myapplication;

/**
 * Created by Amey on 5/7/2015.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Skandy on 5/5/2015.
 */
public class SetUpAlarm {
    AlarmManager alarmManager;
    Context c;
    private PendingIntent pendingIntent;

    public SetUpAlarm(Context c){
        this.c=c;

    }
    public void set(int in_how_many_minutes){

        Intent myIntent = new Intent(c, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(c, 0, myIntent, 0);
        alarmManager=(AlarmManager)c.getSystemService(c.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Log.d("before alarm set :", calendar.get(Calendar.HOUR) + "minute" + calendar.get(Calendar.MINUTE));

        calendar.add(Calendar.MINUTE, in_how_many_minutes);
        Log.d("after alarm set :", calendar.get(Calendar.HOUR) + " after minute" + calendar.get(Calendar.MINUTE));

        alarmManager.set(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("setting:","alarm set intent");
    }

}
