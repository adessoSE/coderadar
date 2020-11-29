package io.reflectoring.coderadar.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnalyzeFileDto {
  private final long id;
  private final String path;
}
