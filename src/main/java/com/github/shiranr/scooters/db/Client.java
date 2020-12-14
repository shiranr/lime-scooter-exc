package com.github.shiranr.scooters.db;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.github.shiranr.scooters.domain.Scooter;

import java.util.Optional;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * This class is singleton. The goal is to have a managed client for our service.
 * Currently, this is not thread safe. Will be done later if needed.
 * The DB HOST and PASSWORD are currently in ENV variables, should be moved to key vault.
 */
public class Client {
    private static CosmosClient cosmos;
    private static Client client;

    static String DB_NAME = "lime";
    static String CONTAINER_NAME = "scooters";

    private Client() {
        if (cosmos == null) {
            String endpoint = System.getenv("DB_HOST");
            String password = System.getenv("DB_PASSWORD");
            cosmos = new CosmosClientBuilder()
                    .endpoint(endpoint)
                    .key(password)
                    .consistencyLevel(ConsistencyLevel.EVENTUAL)
                    .buildClient();
        }
    }

    public static Client GetClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public Scooter[] GetAllScooters() {
        String query = "SELECT * FROM " + CONTAINER_NAME;
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        CosmosPagedIterable iterator = GetContainer().queryItems(query, options, Scooter.class);
        return (Scooter[]) iterator.stream().toArray(Scooter[]::new);
    }

    public Scooter GetScooter(String id) {
        String query = "SELECT * FROM " + CONTAINER_NAME + " c WHERE c.id='" + id + "'";
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions().setMaxBufferedItemCount(1);
        CosmosPagedIterable iterator = GetContainer().queryItems(query, options, Scooter.class);
        Optional first = iterator.stream().findFirst();
        if (first.isPresent()) {
            return (Scooter) first.get();
        }
        return null;
    }

    public Boolean UpdateScooter(Scooter scooter) {
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions().setMaxBufferedItemCount(1);
        CosmosItemResponse response = GetContainer().upsertItem(scooter);
        return verifyStatusCode(response.getStatusCode());
    }

    public boolean CreateScooter(Scooter scooter) {
        CosmosItemResponse itemResponse = GetContainer().createItem(scooter);
        return verifyStatusCode(itemResponse.getStatusCode());
    }

    private CosmosContainer GetContainer() {
        CosmosDatabase db = cosmos.getDatabase(DB_NAME);
        return db.getContainer(CONTAINER_NAME);
    }

    private boolean verifyStatusCode(int statusCode) {
        return statusCode == HTTP_OK || statusCode == HTTP_ACCEPTED || statusCode == HTTP_CREATED;
    }


}
