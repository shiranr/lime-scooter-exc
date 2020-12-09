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

