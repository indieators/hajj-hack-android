package com.nloops.hackathon.models;

public class MedicineModel {

  private String medName;
  private int medDose;
  private boolean isUrgent;
  private boolean isDone;

  public MedicineModel(String medName, int medDose, boolean isUrgent, boolean isDone) {
    this.medName = medName;
    this.medDose = medDose;
    this.isUrgent = isUrgent;
    this.isDone = isDone;
  }

  public String getMedName() {
    return medName;
  }

  public int getMedDose() {
    return medDose;
  }

  public boolean isUrgent() {
    return isUrgent;
  }

  public boolean isDone() {
    return isDone;
  }
}
