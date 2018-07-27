package com.nloops.hackathon.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nloops.hackathon.data.ElementsDBContract.ElementsEntry;

public class ElementsDBHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;

  private static final String DATABASE_NAME = "cards.db";

  private static final String TEXT_TYPE = " TEXT";

  private static final String INTEGER_TYPE = " INTEGER";

  private static final String COMMA = ",";

  private static final String SQL_CREATE_CARDS_ENTRY =
      "CREATE TABLE " + ElementsEntry.TABLE_NAME + " (" +
          ElementsEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
          ElementsEntry.COLUMN_TITLE + TEXT_TYPE + COMMA +
          ElementsEntry.COLUMN_ICON + INTEGER_TYPE + COMMA +
          ElementsEntry.COLUMN_IS_SHOWEN + INTEGER_TYPE + COMMA +
          ElementsEntry.COLUMN_STATUS + TEXT_TYPE +
          ")";


  public ElementsDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_CARDS_ENTRY);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    /*To implement later*/
  }
}
