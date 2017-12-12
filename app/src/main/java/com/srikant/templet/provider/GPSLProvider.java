package com.srikant.templet.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Udini on 6/22/13.
 */
public class GPSLProvider extends ContentProvider {

    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    public static final String LOCATION = "location";                        public static final int LOCATION_TOKEN = 1000;
    public static final String LOCATION_FOR_ID = "location/*";               public static final int LOCATION_FOR_ID_TOKEN = 2000;


    // Uri Matcher for the content provider
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.AUTHORITY;
        matcher.addURI(authority, LOCATION, LOCATION_TOKEN);
        matcher.addURI(authority, LOCATION_FOR_ID, LOCATION_FOR_ID_TOKEN);
        return  matcher;
    }

    // Content Provider stuff
    private DbHelper dbHelper;
    @Override
    public boolean onCreate() {
        Context ctx = getContext();
        dbHelper = new DbHelper(ctx);
        return true;
    }
    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case LOCATION_TOKEN:
                return Contract.LocationTable.CONTENT_TYPE_DIR;
            case LOCATION_FOR_ID_TOKEN:
                return Contract.LocationTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case LOCATION_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.Location.TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, sortOrder, null, null);
            }
            case LOCATION_FOR_ID_TOKEN: {
                int tvShowId = (int)ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.Location.TABLE_NAME);
                builder.appendWhere(DbHelper.Location.ID + "=" + tvShowId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        try {
        switch (token) {

            case LOCATION_TOKEN: {
                long id = db.insert(DbHelper.Location.TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return Contract.LocationTable.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("URI: " + uri + " not supported.");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        String tvShowIdWhereClause;
        switch (token) {
            case (LOCATION_TOKEN):
                rowsDeleted = db.delete(DbHelper.Location.TABLE_NAME, selection, selectionArgs);
                break;
            case (LOCATION_FOR_ID_TOKEN):
                tvShowIdWhereClause = DbHelper.Location.ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    tvShowIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DbHelper.Location.TABLE_NAME, tvShowIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    /**
     * Man..I'm tired..
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case LOCATION_TOKEN: {
            }
            case LOCATION_FOR_ID_TOKEN: {
            }
            default:
                return 0;
        }
    }
}