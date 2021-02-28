package io.reflectoring.coderadar.rest.domain;

import io.reflectoring.coderadar.query.domain.CommitResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAndCommitsForTimePeriodResponse {
  private String path;
  private List<CommitResponse> commits;
}
