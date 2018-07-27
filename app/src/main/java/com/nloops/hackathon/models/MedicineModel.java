package com.nloops.hackathon.models;

public class MedicineModel {

  private String medName;
  private int medDose;
  private boolean isUrgent;

  public MedicineModel(String medName, int medDose, boolean isUrgent) {
    this.medName = medName;
    this.medDose = medDose;
    this.isUrgent = isUrgent;
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
}
