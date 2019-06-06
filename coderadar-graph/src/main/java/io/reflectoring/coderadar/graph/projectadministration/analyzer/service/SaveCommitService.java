package io.reflectoring.coderadar.graph.projectadministration.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.graph.projectadministration.analyzer.repository.SaveCommitRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
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
