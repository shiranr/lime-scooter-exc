package com.github.shiranr.scooters.domain.external;

public class Scooter {
    //Unique ID per scooter
    String id;
    //Battery level in percentage 0 - 100%
    String battery;
    //Is the scooter being used
    boolean checked;
    //The time the scooter was checked in (to be used)
    long checkedIn;

    public Scooter(com.github.shiranr.scooters.domain.internal.Scooter scooter) {
        this.id = scooter.getId();
        this.battery = String.format("%d%%", scooter.getBattery());
        this.checked = scooter.isChecked();
        this.checkedIn = scooter.getCheckedIn();
    }

    public String getId() {
        return id;
    }

    public String getBattery() {
        return battery;
    }

    public boolean isChecked() {
        return checked;
    }

    public long getCheckedIn() {
        return checkedIn;
    }

}