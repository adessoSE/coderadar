package org.wickedsource.coderadar.core.rest.dates.serialize;

import static org.assertj.core.api.Assertions.assertThat;
import static org.wickedsource.coderadar.core.rest.dates.serialize.ObjectMapperProvider.mapper;

import java.io.IOException;
import java.io.StringWriter;
import org.junit.Test;
import org.wickedsource.coderadar.core.rest.dates.Day;

public class DaySerializerTest {

	@Test
	public void serialize() throws IOException {
		Day day = new Day(2016, 5, 13);
		StringWriter writer = new StringWriter();
		mapper().writeValue(writer, day);
		String json = writer.toString();
		assertThat(json).isEqualTo("[2016,5,13]");
	}
}
