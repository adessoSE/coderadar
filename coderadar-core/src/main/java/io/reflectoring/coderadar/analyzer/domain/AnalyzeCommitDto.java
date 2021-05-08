package io.reflectoring.coderadar.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnalyzeCommitDto {
  private final long id;
  private final long hash;
  private final AnalyzeFileDto[] changedFiles;
}
