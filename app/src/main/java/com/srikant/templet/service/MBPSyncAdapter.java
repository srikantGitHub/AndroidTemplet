package com.srikant.templet.service;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.srikant.templet.authentication.AccountGeneral;
import com.srikant.templet.provider.Contract;
import com.srikant.templet.provider.LocationBean;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srikant on 7/1/2017.
 */

public class MBPSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = "MBPSyncAdapter";
    private final AccountManager mAccountManager;
    public MBPSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,ContentProviderClient provider, SyncResult syncResult) {

        try {
            if(extras.get("SYNC_MODLE").equals("incidentreporting")) {
                Cursor cursor = getContext().getContentResolver().query(Contract.LocationTable.CONTENT_URI, null, null, null, null);
                JSONArray jsonArray=cur2Json(cursor);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobj=jsonArray.getJSONObject(i);
                    //new NotificationTask(getContext(), "image", i+2,"http://mobileapi.jhpolice.gov.in/MBP/rest/incidentreporting/uploadMulti", "UTF-8", new File(jsonobj.getString("document")),"files") .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, i+4);
                    //new NotificationTask(getContext(), "audio", i+3,"http://mobileapi.jhpolice.gov.in/MBP/rest/incidentreporting/uploadMulti", "UTF-8", new File(jsonobj.getString("audio")),"files") .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, i+6);
                   // new NotificationTask(getContext(), "video", i+4,"http://mobileapi.jhpolice.gov.in/MBP/rest/incidentreporting/uploadMulti", "UTF-8", new File(jsonobj.getString("video")),"files") .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, i+4);
                    //new NotificationTask(getContext(), "document", i+5,"http://mobileapi.jhpolice.gov.in/MBP/rest/incidentreporting/uploadMulti", "UTF-8", new File(jsonobj.getString("document")),"files") .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, i+10);
                }
                getContext().getContentResolver().delete(Contract.LocationTable.CONTENT_URI,null,null);
            }
            String authToken = mAccountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            String userObjectId = mAccountManager.getUserData(account, AccountGeneral.USERDATA_USER_OBJ_ID);
            MobileAPIServerAccessor parseComService = new MobileAPIServerAccessor();
            Log.d("udinic", TAG + "> Get remote TV Shows");
            // Get shows from remote
            List<LocationBean> remoteTvShows = parseComService.getLocaton(authToken);
            Log.d("udinic", TAG + "> Get local TV Shows");
            // Get shows from local
            ArrayList<LocationBean> localTvShows = new ArrayList<LocationBean>();
            Cursor curTvShows = provider.query(Contract.LocationTable.CONTENT_URI, null, null, null, null);
            if (curTvShows != null) {
                while (curTvShows.moveToNext()) {
                    //localTvShows.add(MainbhipoliceBean.fromCursor(curTvShows));
                }
                curTvShows.close();
            }
            // See what Local shows are missing on Remote
            ArrayList<LocationBean> showsToRemote = new ArrayList<LocationBean>();
            for (LocationBean localTvShow : localTvShows) {
                if (!remoteTvShows.contains(localTvShow))
                    showsToRemote.add(localTvShow);
            }
            // See what Remote shows are missing on Local
            ArrayList<LocationBean> showsToLocal = new ArrayList<LocationBean>();
            for (LocationBean remoteTvShow : remoteTvShows) {
                if (!localTvShows.contains(remoteTvShow) && remoteTvShow.getId() != null) // TODO REMOVE THIS
                    showsToLocal.add(remoteTvShow);
            }
            if (showsToRemote.size() == 0) {
                Log.d("udinic", TAG + "> No local changes to update server");
            } else {
                Log.d("udinic", TAG + "> Updating remote server with local changes");
                // Updating remote tv shows
                for (LocationBean remoteTvShow : showsToRemote) {
                    Log.d("udinic", TAG + "> Local -> Remote [" + remoteTvShow.getLatitude() + "]");
                    parseComService.putLocation(authToken, userObjectId, remoteTvShow);
                }
            }

            if (showsToLocal.size() == 0) {
                Log.d("udinic", TAG + "> No server changes to update local database");
            } else {
                Log.d("udinic", TAG + "> Updating local database with remote changes");

                // Updating local tv shows
                int i = 0;
                ContentValues showsToLocalValues[] = new ContentValues[showsToLocal.size()];
                for (LocationBean localTvShow : showsToLocal) {
                    Log.d("udinic", TAG + "> Remote -> Local [" + localTvShow.getLatitude() + "]");
                    //showsToLocalValues[i++] = localTvShow.getContentValues();
                }
                provider.bulkInsert(Contract.LocationTable.CONTENT_URI, showsToLocalValues);
            }

            Log.d("udinic", TAG + "> Finished.");

        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            syncResult.stats.numAuthExceptions++;
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public JSONArray cur2Json(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }
}

