package com.nloops.hackathon.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nloops.hackathon.R;
import com.nloops.hackathon.adapters.MedicineAdapter.MedicineViewHolder;
import com.nloops.hackathon.models.MedicineModel;
import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineViewHolder> {


  private ArrayList<MedicineModel> medicines;

  public MedicineAdapter(ArrayList<MedicineModel> medicineModels) {
    this.medicines = medicineModels;
  }

  @NonNull
  @Override
  public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    int layoutId = R.layout.medicine_list_item;
    View rootView = LayoutInflater.from(parent.getContext()).inflate(
        layoutId, parent, false);

    return new MedicineViewHolder(rootView);
  }

  public void swapData(ArrayList<MedicineModel> models) {
    this.medicines = models;
    notifyDataSetChanged();
  }

  @Override
  public void onBindViewHolder(@NonNull MedicineViewHolder holder, final int position) {
    MedicineModel currentModel = medicines.get(position);
    holder.mMedName.setText(String.valueOf(currentModel.getMedName()));
    holder.mMedDose.setText(String.valueOf(currentModel.getMedDose()));
    holder.mMedDelete.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        medicines.remove(position);
        notifyDataSetChanged();
      }
    });
  }

  @Override
  public int getItemCount() {
    return (medicines != null) ? medicines.size() : 0;
  }

  class MedicineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_med_item_name)
    TextView mMedName;
    @BindView(R.id.tv_med_item_dose)
    TextView mMedDose;
    @BindView(R.id.ib_med_item_delete)
    ImageButton mMedDelete;


    public MedicineViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
