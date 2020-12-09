package com.github.shiranr.scooters;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;


/**
 * Admin API - This section is restricted by permissions and should only be available to Admins.
 */
public class Admin {

    @FunctionName("history")
    public HttpResponseMessage history(
            @HttpTrigger(name = "history",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "scooter/{id}/history")
                    HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {
        //TODO implement - committing only stubs. Next iteration will include implementation.
        context.getLogger().info(id);
        return request.createResponseBuilder(HttpStatus.OK).body(id).build();
    }
}