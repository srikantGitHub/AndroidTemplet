package com.srikant.templet.service;

/**
 * Created by Srikant on 9/23/2016.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class SheduleReceiver extends BroadcastReceiver {
    private static final long REPEAT_TIME = 1000 * 5;
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent().setAction("startServiceReceiver");
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), REPEAT_TIME, pending);
    }
}