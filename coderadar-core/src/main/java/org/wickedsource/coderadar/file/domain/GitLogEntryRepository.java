package org.wickedsource.coderadar.file.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

public interface GitLogEntryRepository extends CrudRepository<GitLogEntry, Long> {

  List<GitLogEntry> findByCommitNameAndChangeTypeIn(
      String commitName, List<ChangeType> changeTypes);

  int deleteByProjectId(Long projectId);
}
