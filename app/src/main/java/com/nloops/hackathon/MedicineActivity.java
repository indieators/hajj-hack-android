package com.nloops.hackathon;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nloops.hackathon.adapters.MedicineAdapter;
import com.nloops.hackathon.models.MedicineModel;
import java.util.ArrayList;

public class MedicineActivity extends AppCompatActivity {

  @BindView(R.id.et_med_name)
  TextInputEditText mEditMedName;
  @BindView(R.id.et_med_dose)
  TextInputEditText mEditMedDose;
  @BindView(R.id.rv_med_items)
  RecyclerView mRecyclerView;
  @BindView(R.id.lv_med_empty_view)
  TextView mEmptyMedList;
  @BindView(R.id.cb_med_urgent)
  CheckBox mCaseState;
  ArrayList<MedicineModel> medicines;
  MedicineAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_medicine);
    ButterKnife.bind(this);
    Uri data = getIntent().getData();
    medicines = new ArrayList<>();
    mAdapter = new MedicineAdapter(medicines);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
        false);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(mAdapter);

    mEditMedDose.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          if (mEditMedName.getText().length() > 0 &&
              mEditMedDose.getText().length() > 0) {
            addOneMedicine();
            return true;
          }
        }
        return false;
      }
    });


  }

  private void addOneMedicine() {
    medicines.add(new MedicineModel(mEditMedName.getText().toString(),
        Integer.valueOf(mEditMedDose.getText().toString()), mCaseState.isChecked()));
    mAdapter.swapData(medicines);
    mEditMedName.setText("");
    mEditMedDose.setText("");
    mCaseState.setChecked(false);
    mEditMedName.setFocusable(true);

  }
}
