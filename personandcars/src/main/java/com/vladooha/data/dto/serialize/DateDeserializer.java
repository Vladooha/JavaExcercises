package com.vladooha.data.dto.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer extends StdDeserializer<Date> {
    public DateDeserializer() {
        this(null);
    }

    public <T> DateDeserializer(Class<T> tClass) {
        super(tClass);
    }

    @Override
    public Date deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String dateStr = jsonParser.getText();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String noShiftTimeZoneStr = TimeZone.getAvailableIDs()[0];
            TimeZone noShiftTimeZone = TimeZone.getTimeZone(noShiftTimeZoneStr);
            simpleDateFormat.setTimeZone(noShiftTimeZone);

            return simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
