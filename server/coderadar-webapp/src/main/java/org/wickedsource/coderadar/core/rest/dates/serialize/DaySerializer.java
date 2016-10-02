package org.wickedsource.coderadar.core.rest.dates.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.wickedsource.coderadar.core.rest.dates.Day;

import java.io.IOException;

public class DaySerializer extends JsonSerializer<Day> {

    @Override
    public void serialize(Day day, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeArray(new int[]{day.getYear(), day.getMonth(), day.getDayOfMonth()}, 0, 3);
    }

}
