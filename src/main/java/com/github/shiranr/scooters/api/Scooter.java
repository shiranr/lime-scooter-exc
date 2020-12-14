package com.github.shiranr.scooters.api;

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
        //TODO implement - committing only stubs. Next iteration will include implementation.
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
        //TODO implement - committing only stubs. Next iteration will include implementation.
        return request.createResponseBuilder(HttpStatus.OK).build();
    }

}

