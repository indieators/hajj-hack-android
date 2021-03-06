package com.nloops.hackathon;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nloops.hackathon.adapters.ElementsAdapter;
import com.nloops.hackathon.data.ElementsDBContract.ElementsEntry;
import com.nloops.hackathon.models.CardModel;
import com.nloops.hackathon.utils.DataUtils;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>,
    ElementsAdapter.OnCardItemClicked {

  private static final int LOADER_ID = 100;
  @BindView(R.id.main_recycler)
  RecyclerView mRecyclerView;
  private ElementsAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    if (isFirstTimeRun()) {
      createMockData();
    }
    initRecyclerView();
  }

  private void initRecyclerView() {
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,
        false);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(layoutManager);
    mAdapter = new ElementsAdapter(null, this);
    mRecyclerView.setAdapter(mAdapter);
    getSupportLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @NonNull
  @Override
  public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
    return DataUtils.getElementsData(MainActivity.this);
  }

  @Override
  public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    if (data != null && data.getCount() > 0) {
      mAdapter.swapCursor(data);
    }
  }

  @Override
  public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    mAdapter.swapCursor(null);
  }

  @Override
  public void onItemClick(int position) {
    CardModel model = mAdapter.getItem(position);
    Uri elementUri = ContentUris.withAppendedId(ElementsEntry.CONTENT_ELEMENT_URI, model.getID());
    Intent medicineIntent = new Intent(MainActivity.this, MedicineActivity.class);
    medicineIntent.setData(elementUri);
    startActivity(medicineIntent);
  }

  private boolean isFirstTimeRun() {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    return preferences.getBoolean(getString(R.string.str_is_first_run),
        getResources().getBoolean(R.bool.first_run));
  }

  private void createMockData() {
    CardModel[] models = new CardModel[]{new CardModel("Medicine Request", R.drawable.ic_medicine,
        "Running Request", ElementsEntry.STATUS_VISIBLE),
        new CardModel("Campaign Location", R.drawable.ic_map,
            "Heading to Arraft", ElementsEntry.STATUS_VISIBLE),
        new CardModel("Need Help?", R.drawable.ic_phone,
            "", ElementsEntry.STATUS_VISIBLE)};
    for (CardModel model : models) {
      DataUtils.insertMockData(model, MainActivity.this);
    }
  }

}
