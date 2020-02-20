package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToGetDiffsFromCommitsException;
import io.reflectoring.coderadar.vcs.domain.DiffEntry;
import java.util.List;

public interface GetDiffEntriesForCommitsPort {

  List<DiffEntry> getDiffs(String projectRoot, String commitName1, String commitName2)
      throws UnableToGetDiffsFromCommitsException;
}
