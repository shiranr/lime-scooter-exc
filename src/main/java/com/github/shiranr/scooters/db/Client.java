package com.github.shiranr.scooters.db;

public interface Client<T> {
    T[] getAll();
    T get(String id);
    Boolean update(T t);
    void create(T t);
}
