package com.github.shiranr.scooters.api;

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
    /**
     * @param request - no parameter is required
     * @param context - no param is required
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
        //TODO (shiranr) implement - committing only stubs. Next iteration will include implementation.
        return request.createResponseBuilder(HttpStatus.OK).body("Hello").build();
    }
}