package com.nloops.hackathon.data;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import com.nloops.hackathon.data.ElementsDBContract.ElementsEntry;

public class ElementServices extends IntentService {

  public static final String ACTION_INSERT_CARD = "com.nloops.hackathon.ACTIONS.insert";
  public static final String ACTION_DELETE_CARD = "com.nloops.hackathon.ACTIONS.delete";
  public static final String EXTRAS_CONTENT_VALUES = "com.nloops.hackathon.EXTRAS.values";
  private static final String TAG = ElementServices.class.getSimpleName();

  public ElementServices() {
    super(TAG);
  }

  public static void insertNewCard(Context context, ContentValues values) {
    Intent intent = new Intent(context, ElementServices.class);
    intent.setAction(ACTION_INSERT_CARD);
    intent.putExtra(EXTRAS_CONTENT_VALUES, values);
    context.startService(intent);
  }

  public static void deleteCards(Context context) {
    Intent intent = new Intent(context, ElementServices.class);
    intent.setAction(ACTION_DELETE_CARD);
    context.startService(intent);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    assert intent != null;
    if (intent.getAction().equals(ACTION_INSERT_CARD)) {
      ContentValues values = intent.getParcelableExtra(EXTRAS_CONTENT_VALUES);
      performInsertValues(values);
    } else if (intent.getAction().equals(ACTION_DELETE_CARD)) {
      performDeleteValues();
    }
  }

  private void performInsertValues(ContentValues values) {
    getContentResolver().insert(ElementsEntry.CONTENT_ELEMENT_URI, values);
  }

  private void performDeleteValues() {
    int raws = getContentResolver().delete(ElementsEntry.CONTENT_ELEMENT_URI, null, null);
    Log.i(TAG, "performDeleteValues: " + raws);
  }
}
