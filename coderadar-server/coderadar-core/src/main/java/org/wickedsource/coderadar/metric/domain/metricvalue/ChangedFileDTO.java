package org.wickedsource.coderadar.metric.domain.metricvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

@AllArgsConstructor
@Getter
public class ChangedFileDTO {

  private final String oldFileName;

  private final String newFileName;

  private final ChangeType changeType;
}
