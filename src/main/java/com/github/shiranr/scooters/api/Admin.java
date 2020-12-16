package com.github.shiranr.scooters.api;

import com.github.shiranr.scooters.db.Connection;
import com.github.shiranr.scooters.db.CosmosClient;
import com.github.shiranr.scooters.domain.internal.Scooter;
import com.github.shiranr.scooters.service.ScootersService;
import com.github.shiranr.scooters.service.Service;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Admin API - This section is restricted by permissions and should only be available to Admins.
 */
public class Admin {

    //I do not like this but for now I am not using spring so this is not injected.
    Service service = new ScootersService(new CosmosClient(Connection.instance()));

    /**
     *  This method returns the history of a specific scooter
     * @param id -  the scooter id we would like to get the history for
     * @return return the scooter data and its history
     */
    @FunctionName("history")
    public HttpResponseMessage history(
            @HttpTrigger(
                    route = "scooter/{id}/history",
                    name = "history",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ADMIN)
                    HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {
        Scooter scooter = service.GetScooterAdmin(id);
        return request.createResponseBuilder(HttpStatus.OK).body(scooter).build();
    }

    /**
     * This method creates a defined amount of scooters with random data
     * @param amount - the amount of scooters we would like to create in the DB.
     * @return 200 ok when done
     */
    @FunctionName("create")
    public HttpResponseMessage create(
            @HttpTrigger(
                    route = "scooter/{amount}",
                    name = "history",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ADMIN)
                    HttpRequestMessage<Optional<String>> request,
            @BindingName("amount") int amount,
            final ExecutionContext context) {
        IntStream.range(0, amount).mapToObj(i -> random()).forEachOrdered(id -> {
            int battery = randomInt(100);
            service.CreateScooter(id, battery);
        });
        return request.createResponseBuilder(HttpStatus.OK).build();
    }

    private static String random() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static int randomInt(int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(max);
    }
}