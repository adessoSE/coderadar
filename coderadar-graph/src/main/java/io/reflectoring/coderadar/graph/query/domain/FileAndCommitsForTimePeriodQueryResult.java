package io.reflectoring.coderadar.graph.query.domain;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import java.util.List;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
@Data
public class FileAndCommitsForTimePeriodQueryResult {
  private String path;
  private List<CommitEntity> commits;
}
