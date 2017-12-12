package com.srikant.templet.provider;

import android.content.ContentValues;

/**
 * Created by Srikant on 8/12/2016.
 */
public class LocationBean {
    String id;
    String name;
    String latitude;
    String longitude;
    public ContentValues getContentValue(){
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",getName());
        contentValues.put("latitude",getLatitude());
        contentValues.put("longitude",getLongitude());
        return contentValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
