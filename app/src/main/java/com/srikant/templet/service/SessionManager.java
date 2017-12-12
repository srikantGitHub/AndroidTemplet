package com.srikant.templet.service;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
@SuppressLint("CommitPrefEdits")
public class SessionManager {
    SharedPreferences pref;                                    Editor editor;
    Context _context;                                          int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "maibhipolice";    private static final String ALERT_ID = "alert_id";

	public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
      }
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }
    public  void setAlertId(String alertId) {
        editor.putString(ALERT_ID,alertId);
        editor.commit();
    }
    public String getAlertId() {
        return pref.getString(ALERT_ID,"");
    }
   }