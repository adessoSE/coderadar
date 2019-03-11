package org.wickedsource.coderadar.file.domain;

public interface FileRepositoryCustom {

  File findInCommit(String filepath, String commitName, Long projectId);
}
