package com.vladooha.data.dto.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateSerializer extends StdSerializer<Date> {
    public DateSerializer() {
        this(null);
    }

    public <T> DateSerializer(Class<T> tClass) {
        super(tClass, false);
    }

    @Override
    public void serialize(
            Date date,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //jsonGenerator.writeStartObject();
        jsonGenerator.writeString(String.format("%02d.%02d.%04d", day, month, year));
        //jsonGenerator.writeEndObject();
    }
}
