package com.github.shiranr.scooters.service;

import com.github.shiranr.scooters.domain.Scooter;

public interface Service {
    void CreateScooter(String id, int battery);
    boolean CheckInScooter(String id) throws IllegalArgumentException;
    boolean CheckOutScooter(String id) throws IllegalArgumentException;
    Scooter GetScooterAdmin(String id);
    Scooter[] GetAllScooters();
}
