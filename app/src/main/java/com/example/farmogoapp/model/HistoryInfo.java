package com.example.farmogoapp.model;

public class HistoryInfo {

    private String mIncidence;
    private String mMedicine;
    private String mDate;

    public HistoryInfo(String Incidence, String medicine, String date) {
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
}
