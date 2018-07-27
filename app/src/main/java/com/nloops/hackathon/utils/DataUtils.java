package com.nloops.hackathon.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import com.nloops.hackathon.data.ElementServices;
import com.nloops.hackathon.data.ElementsDBContract.ElementsEntry;
import com.nloops.hackathon.models.CardModel;

public class DataUtils {

  /*Empty Constructor*/
  private DataUtils() {
  }

  public static Loader<Cursor> getElementsData(Context context) {
    return new CursorLoader(context,
        ElementsEntry.CONTENT_ELEMENT_URI,
        null, null, null, null);
  }

  public static void insertMockData(CardModel model, Context context) {
    ContentValues values = new ContentValues();
    values.put(ElementsEntry.COLUMN_TITLE, model.getTitle());
    values.put(ElementsEntry.COLUMN_STATUS, model.getStatus());
    values.put(ElementsEntry.COLUMN_ICON, model.getIcon());
    values.put(ElementsEntry.COLUMN_IS_SHOWEN, model.getIsShowen());
    ElementServices.insertNewCard(context, values);
  }

}
