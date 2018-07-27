package com.nloops.hackathon.models;

import android.database.Cursor;
import com.nloops.hackathon.data.ElementsDBContract;
import com.nloops.hackathon.data.ElementsDBContract.ElementsEntry;

public class CardModel {

  private long mID;
  private String mTitle;
  private int mIcon;
  private String mStatus;
  private int isShowen;

  public CardModel(String mTitle, int mIcon, String mStatus, int isShowen) {
    this.mTitle = mTitle;
    this.mIcon = mIcon;
    this.mStatus = mStatus;
    this.isShowen = isShowen;
  }

  public CardModel(Cursor cursor) {
    this.mID = ElementsDBContract.getColumnLong(cursor, ElementsEntry._ID);
    this.mTitle = ElementsDBContract.getColumnString(cursor, ElementsEntry.COLUMN_TITLE);
    this.mStatus = ElementsDBContract.getColumnString(cursor, ElementsEntry.COLUMN_STATUS);
    this.mIcon = ElementsDBContract.getColumnInt(cursor, ElementsEntry.COLUMN_ICON);
  }

  public long getID() {
    return mID;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String mTitle) {
    this.mTitle = mTitle;
  }

  public int getIcon() {
    return mIcon;
  }

  public void setIcon(int mIcon) {
    this.mIcon = mIcon;
  }

  public String getStatus() {
    return mStatus;
  }

  public int getIsShowen() {
    return isShowen;
  }

  public boolean isVisible() {
    return isShowen == ElementsEntry.STATUS_VISIBLE;
  }

  public void setVisibility(int state) {
    this.isShowen = state;
  }


}
