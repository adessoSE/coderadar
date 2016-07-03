package org.wickedsource.coderadar.file.domain;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

import java.util.List;

public interface CommitLogEntryRepository extends CrudRepository<CommitLogEntry, Long> {

    List<CommitLogEntry> findByCommitNameAndChangeTypeIn(String commitName, List<ChangeType> changeTypes);

    int deleteByProjectId(Long projectId);

}