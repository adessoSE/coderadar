package org.wickedsource.coderadar.graph.domain.file;

import lombok.Getter;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
@Getter
public class TouchedFilesCountQueryResult {

  private int addedFiles;
  private int modifiedFiles;
  private int deletedFiles;
  private int renamedFiles;
}
