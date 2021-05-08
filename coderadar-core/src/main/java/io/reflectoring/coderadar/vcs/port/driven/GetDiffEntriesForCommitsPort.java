package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.domain.DiffEntry;
import io.reflectoring.coderadar.vcs.UnableToGetDiffsFromCommitsException;
import java.util.List;

public interface GetDiffEntriesForCommitsPort {

  List<DiffEntry> getDiffs(String projectRoot, String commitName1, String commitName2)
      throws UnableToGetDiffsFromCommitsException;
}
