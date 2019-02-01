package org.wickedsource.coderadar.core.rest.dates.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.Week;

public class ObjectMapperProvider {

	public static ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Day.class, new DayDeserializer());
		module.addDeserializer(Week.class, new WeekDeserializer());
		module.addSerializer(Day.class, new DaySerializer());
		module.addSerializer(Week.class, new WeekSerializer());
		mapper.registerModule(module);
		return mapper;
	}
}
