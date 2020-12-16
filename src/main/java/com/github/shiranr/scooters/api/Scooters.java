package com.github.shiranr.scooters.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.shiranr.scooters.api.mixin.ScooterMixin;
import com.github.shiranr.scooters.db.Connection;
import com.github.shiranr.scooters.db.CosmosClient;
import com.github.shiranr.scooters.domain.Scooter;
import com.github.shiranr.scooters.service.ScootersService;
import com.github.shiranr.scooters.service.Service;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Scooters API - API to manage all scooters. This API is public for all users.
 */
public class Scooters {

    //TODO need to inject the service
    Service service = new ScootersService(new CosmosClient(Connection.instance()));
    /**
     * @return all scooters data
     */
    @FunctionName("scooters")
    public HttpResponseMessage scooters(
            @HttpTrigger(
                    name = "scooters",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        Scooter[] scooters = service.GetAllScooters();
        String scootersString;
        try {
            scootersString = new ScooterMixin().scooterMixin(scooters);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return request.createResponseBuilder(HttpStatus.OK).body(scootersString).build();
    }
}