package org.wickedsource.coderadar.core.rest.dates.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;
import org.wickedsource.coderadar.core.rest.dates.Day;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DayDeserializerTest {

    @Test
    public void deserialize() throws IOException {
        String json = "[2016,5,13]";
        Day day = mapper().readValue(json, Day.class);
        assertThat(day.getYear()).isEqualTo(2016);
        assertThat(day.getMonth()).isEqualTo(5);
        assertThat(day.getDayOfMonth()).isEqualTo(13);
    }

    @Test(expected = IllegalStateException.class)
    public void deserializeError() throws IOException {
        String json = "[2016,5,13,15]";
        mapper().readValue(json, Day.class);
    }

    private ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Day.class, new DayDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

}