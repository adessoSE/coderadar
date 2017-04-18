package org.wickedsource.coderadar.graph.domain.file;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class TouchedFilesCountQueryResult {

  int addedFiles;
  int modifiedFiles;
  int deletedFiles;
  int renamedFiles;

  public int getAddedFiles() {
    return addedFiles;
  }

  public int getModifiedFiles() {
    return modifiedFiles;
  }

  public int getDeletedFiles() {
    return deletedFiles;
  }

  public int getRenamedFiles() {
    return renamedFiles;
  }
}
