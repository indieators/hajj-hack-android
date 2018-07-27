package com.nloops.hackathon.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import com.nloops.hackathon.BuildConfig;

/**
 * This class will holds {@link SQLiteDatabase} Tables fields.
 */
public class ElementsDBContract {

  // General variables declaration that use to build content URI
  // to access data using Content Provider.
  public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
  private static final String CONTENT_SCHEME = "content://";
  public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

  /*Empty Constructor to prevent calling*/
  private ElementsDBContract() {
  }

  /* Helpers to retrieve column values */
  public static String getColumnString(Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndex(columnName));
  }

  public static int getColumnInt(Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndex(columnName));
  }

  public static long getColumnLong(Cursor cursor, String columnName) {
    return cursor.getLong(cursor.getColumnIndex(columnName));
  }

  public static abstract class ElementsEntry implements BaseColumns {

    public static final String TABLE_NAME = "cards";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_IS_SHOWEN = "visibility";
    //Content Uri for table task
    public static final Uri CONTENT_ELEMENT_URI = BASE_CONTENT_URI.buildUpon()
        .appendPath(TABLE_NAME).build();
    /*Declare Table Constants*/
    public static final int STATUS_VISIBLE = 0;
    public static final int STATUS_INVISIBLE = 1;

  }

}
