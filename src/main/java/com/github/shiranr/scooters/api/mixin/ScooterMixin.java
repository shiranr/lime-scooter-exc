package com.github.shiranr.scooters.api.mixin;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.shiranr.scooters.domain.Scooter;

public class ScooterMixin {
    public String scooterMixin(Scooter[] scooter) throws JsonProcessingException {
        String[] propertiesToExclude = {"history"};
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(Object.class, DynamicMixIn.class);
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("dynamicFilter", SimpleBeanPropertyFilter.serializeAllExcept(propertiesToExclude));
        mapper.setFilterProvider(filterProvider);
        return mapper.writeValueAsString(scooter);
    }
}

@JsonFilter("dynamicFilter")
class DynamicMixIn {
}

