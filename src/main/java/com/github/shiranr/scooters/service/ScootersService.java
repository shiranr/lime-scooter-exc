package com.github.shiranr.scooters.service;

import com.github.shiranr.scooters.db.Client;
import com.github.shiranr.scooters.domain.Scooter;
import com.github.shiranr.scooters.exceptions.InvalidStateException;

import java.util.ArrayList;
import java.util.Date;

/**
 * The service manages the scooters against the DB. CRUD actions.
 * In an optimal world, this would be singleton and injected with spring. For now it is being created by the controllers.
 */
public class ScootersService implements Service {

    final private Client<Scooter> client;

    public ScootersService(Client<Scooter> client) {
        this.client = client;
    }

    public void CreateScooter(String id, int battery) {
        Scooter scooter = new Scooter(id, battery, false, 0, new ArrayList<>());
        client.create(scooter);
    }

    public boolean CheckInScooter(String id) throws IllegalArgumentException {
        Scooter scooter = client.get(id);
        //TODO not thread safe
        if (scooter.isChecked()) {
            throw new InvalidStateException("failed to check in scooter. scooter " + id + " already checked in");
        }
        scooter.setChecked(true);
        scooter.setCheckedIn(new Date().getTime());
        return client.update(scooter);
    }

    public boolean CheckOutScooter(String id) throws IllegalArgumentException {
        Scooter scooter = client.get(id);
        if (!scooter.isChecked()) {
            throw new InvalidStateException("failed to check out scooter. scooter " + id + " already checked out");
        }
        scooter.setChecked(false);
        long checkInTime = scooter.getCheckedIn();
        scooter.addHistory(checkInTime);
        scooter.setCheckedIn(0);
        return client.update(scooter);
    }

    public Scooter GetScooterAdmin(String id) {
        return client.get(id);
    }

    public Scooter GetScooterPublic(String id) {
        return client.get(id);
    }

    public Scooter[] GetAllScooters() {
        return client.getAll();
    }

}
