package com.github.shiranr.scooters.db;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.github.shiranr.scooters.domain.internal.Scooter;

import java.util.Optional;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

public class CosmosClient implements Client<Scooter>{

    private Connection connection;

    public CosmosClient(Connection connection) {
        this.connection = connection;
    }

    public Scooter[] getAll() {
        String query = "SELECT * FROM " + connection.getContainerName();
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        CosmosPagedIterable iterator = connection.getContainer().queryItems(query, options, Scooter.class);
        return (Scooter[]) iterator.stream().toArray(Scooter[]::new);
    }

    public Scooter get(String id) {
        String query = "SELECT * FROM " + connection.getContainerName() + " c WHERE c.id='" + id + "'";
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions().setMaxBufferedItemCount(1);
        CosmosPagedIterable iterator = connection.getContainer().queryItems(query, options, Scooter.class);
        Optional first = iterator.stream().findFirst();
        if (first.isPresent()) {
            return (Scooter) first.get();
        }
        return null;
    }

    public Boolean update(Scooter scooter) {
        CosmosItemResponse response = connection.getContainer().upsertItem(scooter);
        return verifyStatusCode(response.getStatusCode());
    }

    public void create(Scooter scooter) {
        CosmosItemResponse itemResponse = connection.getContainer().createItem(scooter);
        if (!verifyStatusCode(itemResponse.getStatusCode())) {
            System.out.println("failed to create a scooter with status code " + itemResponse.getStatusCode());
        }
    }

    private boolean verifyStatusCode(int statusCode) {
        return statusCode == HTTP_OK || statusCode == HTTP_ACCEPTED || statusCode == HTTP_CREATED;
    }
}
