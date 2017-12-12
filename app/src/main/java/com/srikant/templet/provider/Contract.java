package com.srikant.templet.provider;

import android.net.Uri;

import java.util.HashMap;

/**
 * Holds the API to the content provider
 *
 * Created by Udini on 7/2/13.
 */
public class Contract {
    public static final String AUTHORITY = "com.jhpolice.gpsl_provider";
    public static  class LocationTable
    {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gps.location";
        public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.gps.location";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/location");
    }
}
