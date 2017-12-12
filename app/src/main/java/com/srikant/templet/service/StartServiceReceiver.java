package com.srikant.templet.service;

/**
 * Created by Srikant on 9/23/2016.
 */
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent i = new Intent();
            i.setClass(context,LocalService.class);
            PendingIntent pending = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            pending.send(context, 0, i);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
}