package io.reflectoring.coderadar.query.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Arrays;

public class WeekDeserializer extends JsonDeserializer<Week> {

  @Override
  public Week deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    int[] coordinates = p.readValueAs(int[].class);
    if (coordinates.length != 2) {
      throw new IllegalStateException(
          String.format(
              "expecting exactly 2 array elements but got: %s!", Arrays.toString(coordinates)));
    }
    return new Week(coordinates[0], coordinates[1]);
  }
}
