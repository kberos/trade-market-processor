package com.currencyfair.messageprocessor;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageDateDeserializer extends JsonDeserializer<Date> {

    private static SimpleDateFormat formatter =
            new SimpleDateFormat("dd-MMM-yy hh:mm:ss");

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException {
        String date = jsonparser.getText();
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}