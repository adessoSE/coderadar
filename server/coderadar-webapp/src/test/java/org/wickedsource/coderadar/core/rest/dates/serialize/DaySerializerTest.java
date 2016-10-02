package org.wickedsource.coderadar.core.rest.dates.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;
import org.wickedsource.coderadar.core.rest.dates.Day;

import java.io.IOException;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySerializerTest {

    @Test
    public void serialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Day.class, new DaySerializer());
        mapper.registerModule(module);
        Day day = new Day(2016, 5, 13);
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, day);
        String json = writer.toString();
        assertThat(json).isEqualTo("[2016,5,13]");
    }

}