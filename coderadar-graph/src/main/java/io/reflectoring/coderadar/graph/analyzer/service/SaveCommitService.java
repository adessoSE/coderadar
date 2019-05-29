package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.SaveCommitPort;
import io.reflectoring.coderadar.core.projectadministration.domain.Commit;
import io.reflectoring.coderadar.graph.analyzer.repository.SaveCommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SaveCommitService")
public class SaveCommitService implements SaveCommitPort {
  private final SaveCommitRepository saveCommitRepository;

  @Autowired
  public SaveCommitService(SaveCommitRepository saveCommitRepository) {
    this.saveCommitRepository = saveCommitRepository;
  }

  @Override
  public void saveCommit(Commit commit) {
    saveCommitRepository.save(commit);
  }
}
