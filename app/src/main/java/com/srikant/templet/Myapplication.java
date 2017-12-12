package com.srikant.templet;
import android.accounts.Account;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.srikant.templet.Myobserver.Subject;
import com.srikant.templet.service.SheduleReceiver;
import com.srikant.templet.service.StartServiceReceiver;

/**
 * Created by Srikant on 7/4/2017.
 */

public class Myapplication extends Application {
    private static Context mContext;
    public static Subject registrationSubject=new Subject();
    public static SheduleReceiver sheduleReceiver=new SheduleReceiver();
    public static StartServiceReceiver startServiceReceiver=new StartServiceReceiver();
    public static Account mConnectedAccount;
    @Override
    public void onCreate() {
        super.onCreate();
        Myapplication.mContext = getApplicationContext();
    }
    public boolean isInternetAvailable() {
        try {
            boolean haveConnectedWifi = false;
            boolean haveConnectedMobile = false;
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;
        } catch (Exception e) {
            return false;
        }

    }
    public void registerReceiver(){
        try {
            registerReceiver(sheduleReceiver, new IntentFilter("sheduleReceiver"));
            registerReceiver(startServiceReceiver, new IntentFilter("startServiceReceiver"));
            sendBroadcast(new Intent("sheduleReceiver"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void unregisterReceiver(){
        try {
            unregisterReceiver(sheduleReceiver);
            unregisterReceiver(startServiceReceiver);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
