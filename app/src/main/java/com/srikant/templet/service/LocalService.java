package com.srikant.templet.service;
/**
 * Created by Srikant on 9/23/2016.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;

import com.srikant.templet.R;
import com.srikant.templet.ui.Home;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LocalService extends Service {
    private final IBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            InserSerivices serivices=new InserSerivices();
            serivices.execute().get(10, TimeUnit.SECONDS);
            JSONObject obj=new JSONObject(serivices.message);
            String alertid=obj.getString("alert_id");
            String appalertid=new SessionManager(getApplicationContext()).getAlertId();
            if(!(alertid.equals(appalertid))) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent mynotification = new Intent(getApplicationContext(), Home.class);
                mynotification.putExtra("message",obj.getString("alert_message"));
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mynotification, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                Notification notification = builder.setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher_background).setTicker("Main Bhi Police").setWhen(System.currentTimeMillis())
                        .setAutoCancel(true).setContentTitle("MyNotification From Jhakhand Police")
                        .setContentText(obj.getString("alert_message")).build();
                notificationManager.notify(10001, notification);
                new SessionManager(getApplicationContext()).setAlertId(obj.getString("alert_id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Service.START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }
class InserSerivices extends AsyncTask {
    String filter;
    String message;
    @Override
    protected Void doInBackground(Object[] objects) {
        try {
            URL url = new URL("https://mobileapi.jhpolice.gov.in/MobileAPI/mobile/MBP/getAlert");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String userCredentials = "srikant:pari";
            String basicAuth = "Basic " + new String(Base64.encode(userCredentials.getBytes(),0));
            connection.setRequestProperty ("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);
            String line;
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null){sb.append(line + "\n");}
           message = String.valueOf(sb);
            isr.close();
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
}