package org.wickedsource.coderadar.core.rest.dates.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.wickedsource.coderadar.core.rest.dates.Week;

import java.io.IOException;

public class WeekSerializer extends JsonSerializer<Week> {

    @Override
    public void serialize(Week week, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeArray(new int[]{week.getYear(), week.getWeekOfYear()}, 0, 2);
    }

}
