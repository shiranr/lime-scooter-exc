package com.github.shiranr.scooters;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import netscape.javascript.JSObject;

import java.util.Optional;

public class Scooters {

    @FunctionName("scooters")
    public HttpResponseMessage scooters(
            @HttpTrigger(
                    name = "scooters",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

            //TODO implement
            return request.createResponseBuilder(HttpStatus.OK).body("Hello").build();

    }
}
