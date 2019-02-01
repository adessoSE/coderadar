package org.wickedsource.coderadar.core.rest.dates.serialize;

import static org.assertj.core.api.Assertions.assertThat;
import static org.wickedsource.coderadar.core.rest.dates.serialize.ObjectMapperProvider.mapper;

import java.io.IOException;
import org.junit.Test;
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.Week;

public class WeekDeserializerTest {

	@Test
	public void deserialize() throws IOException {
		String json = "[2016,5]";
		Week week = mapper().readValue(json, Week.class);
		assertThat(week.getYear()).isEqualTo(2016);
		assertThat(week.getWeekOfYear()).isEqualTo(5);
	}

	@Test(expected = IllegalStateException.class)
	public void deserializeError() throws IOException {
		String json = "[2016,5,13,15]";
		mapper().readValue(json, Day.class);
	}
}
