package com.nloops.hackathon;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
  @BindView(R.id.bt_med_submit)
  Button mSubmitButton;
  ArrayList<MedicineModel> medicines;
  MedicineAdapter mAdapter;

  /*Firebase ref*/
  private String mUsername;
  private FirebaseDatabase mFireDataBase;
  private DatabaseReference mFireDatabaseReference;
  private ChildEventListener mChildEventListener;

  /**
   * Helper Method hiding SoftKeyboard OnClick
   */
  private static void hideSoftKeyboard(Activity activity) {
    InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(
        activity.getCurrentFocus().getWindowToken(), 0);
  }

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

    mSubmitButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        for (int i = 0; i < medicines.size(); i++) {
          MedicineModel currentModel = medicines.get(i);
          mFireDatabaseReference.push().setValue(currentModel);
        }
        finish();
      }
    });

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String userUID = preferences.getString(getString(R.string.key_user_name), "");

    /*Setup Firebase DB*/
    mFireDataBase = FirebaseDatabase.getInstance();
    mFireDatabaseReference = mFireDataBase.getReference().child("campaigns")
        .child("singlecampaign").child("userUID").child("medicinereq");


  }

  private void addOneMedicine() {
    medicines.add(new MedicineModel(mEditMedName.getText().toString(),
        Integer.valueOf(mEditMedDose.getText().toString()), mCaseState.isChecked(), false));
    mAdapter.swapData(medicines);
    mEditMedName.setText("");
    mEditMedDose.setText("");
    mCaseState.setChecked(false);
    hideSoftKeyboard(MedicineActivity.this);

  }

}
