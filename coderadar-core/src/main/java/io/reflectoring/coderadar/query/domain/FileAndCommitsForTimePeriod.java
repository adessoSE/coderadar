package io.reflectoring.coderadar.query.domain;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAndCommitsForTimePeriod {
  private String path;
  private List<Commit> commits;
}
