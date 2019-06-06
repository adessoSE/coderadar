package io.reflectoring.coderadar.analyzer.domain;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangedFileDTO {

  private final String oldFileName;

  private final String newFileName;

  private final ChangeType changeType;
}
