package com.srikant.templet.service;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Srikant on 7/1/2017.
 */
public class MBPSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MBPSyncAdapter sSyncAdapter = null;
    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new MBPSyncAdapter(getApplicationContext(), true);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
