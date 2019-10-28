package com.example.farmogoapp.ui.main.farmStats;

public class FarmHistory {

    private String mAnimalId;
    private String mIncidence;
    private String mMedicine;
    private String mDate;

    public FarmHistory( String animalId, String Incidence, String medicine, String date) {
        mAnimalId = animalId;
        mIncidence = Incidence;
        mMedicine = medicine;
        mDate = date;
    }

    public String getmIncidence() {
        return mIncidence;
    }

    public void setmIncidence(String mIncidence) {
        this.mIncidence = mIncidence;
    }

    public String getmMedicine() {
        return mMedicine;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmAnimalId() { return mAnimalId; }

    public void setmAnimalId(String mAnimalId) { this.mAnimalId = mAnimalId; }
}
