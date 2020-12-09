package com.github.shiranr.scooters;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class Admin {

    @FunctionName("history")
    public HttpResponseMessage history(
            @HttpTrigger(
                    route = "scooter/{id}/history",
                    name = "history",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ADMIN)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        //TODO implement
        return request.createResponseBuilder(HttpStatus.OK).build();

    }
}
