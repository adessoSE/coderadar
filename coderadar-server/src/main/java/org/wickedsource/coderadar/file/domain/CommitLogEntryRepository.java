package org.wickedsource.coderadar.file.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

public interface CommitLogEntryRepository extends CrudRepository<CommitLogEntry, Long> {

  List<CommitLogEntry> findByCommitNameAndChangeTypeIn(
      String commitName, List<ChangeType> changeTypes);

  int deleteByProjectId(Long projectId);
}
