package com.cookandroid.bottom_setting;

import android.graphics.drawable.Drawable;

public class List_ListviewItem {

    private String goalStr;
    private String sdateStr;
    private String edateStr;
    private String etcStr;
    private String perStr;
    private Drawable isDoneDrawable;

    public void setGoal(String goal) { goalStr = goal; }
    public void setSdate(String sdate) {
        sdateStr = sdate;
    }
    public void setEdate(String edate) {
        edateStr = edate;
    }
    public void setEtc(String etc) {
        etcStr = etc;
    }
    public void setPer(String per) { perStr = per; }
    public void setIsDone(Drawable isDone) {
        isDoneDrawable = isDone;
    }

    public String getGoal() { return this.goalStr; }
    public String getSdate() {
        return this.sdateStr;
    }
    public String getEdate() { return this.edateStr; }
    public String getEtc() {
        return this.etcStr;
    }
    public String getPer() {
        return this.perStr;
    }
    public Drawable getIsDone() { return this.isDoneDrawable; }
}
