package com.nloops.hackathon.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.nloops.hackathon.data.ElementsDBContract.ElementsEntry;

/**
 * This Class will Act like a layer between View and Database.
 */
public class ElementsContentProvider extends ContentProvider {

  private static final String TAG = ElementsContentProvider.class.getSimpleName();
  private static final int ELEMENTS = 100;
  private static final int ELEMENT_ITEM = 101;
  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private ElementsDBHelper mDbHelper;

  /**
   * This method will analyze passed URI and choose which CRUD situation will perform.
   *
   * @return {@link UriMatcher}
   */
  private static UriMatcher buildUriMatcher() {
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = ElementsDBContract.CONTENT_AUTHORITY;
    matcher.addURI(authority, ElementsEntry.TABLE_NAME, ELEMENTS);
    matcher.addURI(authority, ElementsEntry.TABLE_NAME + "/#", ELEMENT_ITEM);
    return matcher;
  }

  @Override
  public boolean onCreate() {
    mDbHelper = new ElementsDBHelper(getContext());
    return true;
  }


  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
      /*This case we will get all Data stored in DB*/
      case ELEMENTS:
        retCursor = mDbHelper.getReadableDatabase().query(
            ElementsEntry.TABLE_NAME,
            projection, selection, selectionArgs, sortOrder, null, null);
        break;
      /*This case we will query specific item by its ID*/
      case ELEMENT_ITEM:
        String[] where = {uri.getLastPathSegment()};
        retCursor = mDbHelper.getReadableDatabase().query(ElementsEntry.TABLE_NAME,
            projection,
            ElementsEntry._ID + " = ?",
            where,
            null,
            null,
            sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
  }


  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    long rawID;
    switch (sUriMatcher.match(uri)) {
      case ELEMENTS:
        rawID = mDbHelper.getWritableDatabase().insert(
            ElementsEntry.TABLE_NAME, null, values);
        break;
      default:
        throw new UnsupportedOperationException("Insert not supported for " + uri);
    }
    // if we failed to insert the raw
    if (rawID == -1) {
      Log.i(TAG, "failed to insert new task");
      return null;
    } else {
      getContext().getContentResolver().notifyChange(uri, null);
      return ContentUris.withAppendedId(uri, rawID);
    }
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    int rawsUpdated;
    int i = sUriMatcher.match(uri);
    if (i == ELEMENT_ITEM) {
      selection = ElementsEntry._ID + "=?";
      selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
      rawsUpdated = mDbHelper.getWritableDatabase().update(
          ElementsEntry.TABLE_NAME,
          values,
          selection,
          selectionArgs
      );

    } else {
      throw new UnsupportedOperationException("Updated not supported for " + uri);
    }
    if (rawsUpdated <= 0) {
      Log.i(TAG, "update failed.");
      return 0;
    } else {
      getContext().getContentResolver().notifyChange(uri, null);
      return rawsUpdated;
    }
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    int match = sUriMatcher.match(uri);
    int effectedRaws = 0;
    if (match == ELEMENTS) {
      effectedRaws = mDbHelper.getWritableDatabase().delete(ElementsEntry.TABLE_NAME, null, null);

    }
    if (effectedRaws != 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return effectedRaws;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }
}
