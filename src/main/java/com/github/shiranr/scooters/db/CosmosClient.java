package com.github.shiranr.scooters.db;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.github.shiranr.scooters.domain.internal.Scooter;
import com.github.shiranr.scooters.exceptions.InvalidIDException;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

public class CosmosClient implements Client<Scooter>{

    private Connection connection;
    Logger logger = Logger.getLogger(CosmosClient.class.getName());

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
        throw new InvalidIDException("failed to find scooter. invalid id " + id);
    }

    public Boolean update(Scooter scooter) {
        CosmosItemResponse response = connection.getContainer().upsertItem(scooter);
        return verifyStatusCode(response.getStatusCode());
    }

    public void create(Scooter scooter) {
        CosmosItemResponse itemResponse = connection.getContainer().createItem(scooter);
        if (!verifyStatusCode(itemResponse.getStatusCode())) {
            String error = "failed to create a scooter with status code " + itemResponse.getStatusCode();
            logger.log(Level.WARNING, error);
        }
    }

    private boolean verifyStatusCode(int statusCode) {
        return statusCode == HTTP_OK || statusCode == HTTP_ACCEPTED || statusCode == HTTP_CREATED;
    }
}
