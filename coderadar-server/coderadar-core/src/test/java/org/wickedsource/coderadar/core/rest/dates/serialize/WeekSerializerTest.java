package org.wickedsource.coderadar.core.rest.dates.serialize;

import static org.assertj.core.api.Assertions.assertThat;
import static org.wickedsource.coderadar.core.rest.dates.serialize.ObjectMapperProvider.mapper;

import java.io.IOException;
import java.io.StringWriter;
import org.junit.Test;
import org.wickedsource.coderadar.core.rest.dates.Week;

public class WeekSerializerTest {

	@Test
	public void serialize() throws IOException {
		Week week = new Week(2016, 5);
		StringWriter writer = new StringWriter();
		mapper().writeValue(writer, week);
		String json = writer.toString();
		assertThat(json).isEqualTo("[2016,5]");
	}
}
