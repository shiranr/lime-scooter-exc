package com.github.shiranr.scooters.api;

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
    /**
     *  This method returns the history of a specific scooter
     * @param id -  the scooter id we would like to get the history for
     * @return return the scooter data and its history
     */
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

    /**
     * This method creates a defined amount of scooters with random data
     * @param amount - the amount of scooters we would like to create in the DB.
     * @return 200 ok when done
     */
    @FunctionName("create")
    public HttpResponseMessage create(
            @HttpTrigger(name = "create",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "scooter/create/{amount}")
                    HttpRequestMessage<Optional<String>> request,
            @BindingName("amount") int amount,
            final ExecutionContext context) {
        //TODO implement - committing only stubs. Next iteration will include implementation.
        return request.createResponseBuilder(HttpStatus.OK).body(amount).build();
    }
}