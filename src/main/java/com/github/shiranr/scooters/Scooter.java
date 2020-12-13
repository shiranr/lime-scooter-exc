package com.github.shiranr.scooters;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class Scooter {
    @FunctionName("checkin")
    public HttpResponseMessage checkin(
            @HttpTrigger(
                    route = "scooter/{id}/check/in",
                    name = "checkin",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        //TODO implement
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
            final ExecutionContext context) {

        //TODO implement
        return request.createResponseBuilder(HttpStatus.OK).build();

    }

}

