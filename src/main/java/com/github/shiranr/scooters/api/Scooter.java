package com.github.shiranr.scooters.api;

import com.github.shiranr.scooters.db.Connection;
import com.github.shiranr.scooters.db.CosmosClient;
import com.github.shiranr.scooters.exceptions.InvalidIDException;
import com.github.shiranr.scooters.exceptions.InvalidStateException;
import com.github.shiranr.scooters.service.ScootersService;
import com.github.shiranr.scooters.service.Service;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Scooter API - a public API for all type of users to manage a single scooter
 */
public class Scooter {

    //I do not like this but for now I am not using spring so this is not injected.
    Service service = new ScootersService(new CosmosClient(Connection.instance()));

    /**
     * check in a scooter so it will be yours for riding
     * @param id - the id of the scooter we would like to check in
     * @return success or failure of checking in the scooter
     */
    @FunctionName("checkin")
    public HttpResponseMessage checkin(
            @HttpTrigger(
                    route = "scooter/{id}/check/in",
                    name = "checkin",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {
        if (id.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("id can not be empty").build();
        }
        try {
            boolean success = service.CheckInScooter(id);
            if (!success) {
                return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to check in scooter with id: " + id).build();
            }
        } catch (InvalidIDException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("scooter with id: " + id + " was not found").build();
        } catch (InvalidStateException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("scooter with id: " + id + " is already checked in").build();
        }
        return request.createResponseBuilder(HttpStatus.OK).build();
    }

    /**
     *  checkout the scooter once you are done riding it and others can have it
     * @param id - the id of the scooter we would like to check out
     * @return success or failure of checking out the scooter
     */
    @FunctionName("checkout")
    public HttpResponseMessage checkout(
            @HttpTrigger(
                    route = "scooter/{id}/check/out",
                    name = "checkout",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {
        if (id.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("id can not be empty").build();
        }
        try {
            boolean success = service.CheckOutScooter(id);
            if (!success) {
                return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to check out scooter with id: " + id).build();
            }
        } catch (InvalidIDException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("scooter with id: " + id + " was not found").build();
        } catch (InvalidStateException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("scooter with id: " + id + " is already checked out").build();
        }
        return request.createResponseBuilder(HttpStatus.OK).build();
    }

}

