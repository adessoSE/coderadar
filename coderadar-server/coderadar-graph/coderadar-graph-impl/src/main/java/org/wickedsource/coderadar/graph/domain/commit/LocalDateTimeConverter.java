package org.wickedsource.coderadar.graph.domain.commit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Long> {

  @Override
  public Long toGraphProperty(LocalDateTime dateTime) {
    return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
  }

  @Override
  public LocalDateTime toEntityAttribute(Long millis) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
  }
}
