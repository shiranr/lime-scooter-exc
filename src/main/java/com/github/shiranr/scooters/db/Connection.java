package com.github.shiranr.scooters.db;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;

/**
 * This class is singleton. The goal is to have a managed client for our service.
 * The DB HOST and PASSWORD are currently in ENV variables, should be moved to key vault.
 */
public class Connection {
    private static CosmosClient client;
    private static Connection connection;

    private static String DB_NAME = "lime";
    private static String CONTAINER_NAME = "scooters";

    private Connection() {
        if (client == null) {
            String endpoint = System.getenv("DB_HOST");
            if (endpoint.equals("")) {
                throw new IllegalArgumentException("failed to get DB endpoint");
            }
            String password = System.getenv("DB_PASSWORD");
            if (password.equals("")) {
                throw new IllegalArgumentException("failed to get DB password");
            }
            client = new CosmosClientBuilder()
                    .endpoint(endpoint)
                    .key(password)
                    .consistencyLevel(ConsistencyLevel.EVENTUAL)
                    .buildClient();
        }
    }

    public static Connection instance() {
        if (connection == null) {
            synchronized (Connection.class) {
                if (connection == null) {
                    connection = new Connection();
                }
            }
        }
        return connection;
    }

    public CosmosContainer getContainer() {
        CosmosDatabase db = client.getDatabase(DB_NAME);
        return db.getContainer(CONTAINER_NAME);
    }

    public String getContainerName() {
        return CONTAINER_NAME;
    }
}
