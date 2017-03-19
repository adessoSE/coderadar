package org.wickedsource.coderadar.file.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

public interface GitLogEntryRepository extends CrudRepository<GitLogEntry, Long> {

  List<GitLogEntry> findByCommitNameAndChangeTypeIn(
      String commitName, List<ChangeType> changeTypes);

  int deleteByProjectId(Long projectId);

  int countByProjectIdAndFilepathAndChangeTypeAndFileHash(
      long projectId, String filepath, ChangeType changeType, String fileHash);

  List<GitLogEntry> findByCommitId(long commitId);

  List<GitLogEntry> findByCommitIdAndChangeTypeIn(
      long commitId, Collection<ChangeType> changeTypes);
}
