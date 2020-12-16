package com.github.shiranr.scooters.domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * This is a scooter entity, it represents scooter both in the DB and FE.
 * In the future, should be separated into 2 entities so the DB scheme and FE scheme won't be coupled.
 * IMPORTANT!!!! currently this is both DB object and FE object, this is NOT a good practice.
 */
public class Scooter {
    //Unique ID per scooter
    String id;
    //Battery level in percentage 0 - 100
    int battery;
    //Is the scooter being used
    boolean checked;
    //The time the scooter was checked in (to be used)
    long checkedIn;
    //The scooter history of check in and out.
    ArrayList<HistoryRecord> history;

    public Scooter() {
    }

    public Scooter(String id, int battery, boolean checked, long checkedIn, ArrayList<HistoryRecord> history) {
        this.id = id;
        this.battery = battery;
        this.checked = checked;
        this.checkedIn = checkedIn;
        this.history = history;
    }

    public String getId() {
        return id;
    }

    public int getBattery() {
        return battery;
    }

    public boolean isChecked() {
        return checked;
    }

    public long getCheckedIn() {
        return checkedIn;
    }

    public ArrayList<HistoryRecord> getHistory() {
        return history;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setCheckedIn(long checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void addHistory(long checkedIn) {
        HistoryRecord record = new HistoryRecord(checkedIn, new Date().getTime());
        this.history.add(record);
    }
}