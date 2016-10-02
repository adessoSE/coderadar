package org.wickedsource.coderadar.core.rest.dates.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.wickedsource.coderadar.core.rest.dates.Day;

import java.io.IOException;
import java.util.Arrays;

public class DayDeserializer extends JsonDeserializer<Day> {

    @Override
    public Day deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int[] coordinates = p.readValueAs(int[].class);
        if (coordinates.length != 3) {
            throw new IllegalStateException(String.format("expecting exactly 3 array elements but got: %s!", Arrays.toString(coordinates)));
        }
        return new Day(coordinates[0], coordinates[1], coordinates[2]);
    }

}
