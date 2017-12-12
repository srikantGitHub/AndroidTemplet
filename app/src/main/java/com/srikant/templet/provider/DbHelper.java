package com.srikant.templet.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Udini on 6/22/13.
 */
public class DbHelper extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;

    public static class Location {
        public static final String TABLE_NAME = "location";
        public static final String ID = "_id";  public static final String NAME = "name";
        public static final String LATITUDE = "latitude";  public static final String LONGITUDE = "longitude";
        // Database creation sql statement
        public static final String CREATE_LOCATION =  "create table "
                + TABLE_NAME + "(" + ID + " integer   primary key autoincrement, "
                + NAME + " text, "+ LATITUDE + " text, "+ LONGITUDE + " text "+ ");";
    }


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Location.CREATE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Location.TABLE_NAME);
        onCreate(db);
    }
}
