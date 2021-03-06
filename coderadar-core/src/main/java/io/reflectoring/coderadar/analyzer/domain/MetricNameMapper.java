package io.reflectoring.coderadar.analyzer.domain;

public class MetricNameMapper {

  private MetricNameMapper() {}

  public static String mapToString(int value) {
    return MetricName.valueOfInt(value).getName();
  }

  public static int mapToInt(String name) {
    return MetricName.valueOfString(name).getIntegerValue();
  }
}
