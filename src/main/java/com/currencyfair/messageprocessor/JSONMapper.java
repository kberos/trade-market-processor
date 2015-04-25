package com.currencyfair.messageprocessor;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.util.Date;

public class JSONMapper extends ObjectMapper {

    public JSONMapper() {
        SimpleModule module = new SimpleModule("JSONModule", new Version(2, 0, 0, null, null, null));
        module.addSerializer(Date.class, new DateSerializer());
        module.addDeserializer(Date.class, new MessageDateDeserializer());
        registerModule(module);
    }

}
