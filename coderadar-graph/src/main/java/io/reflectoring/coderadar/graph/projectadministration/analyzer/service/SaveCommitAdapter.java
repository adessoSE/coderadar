package io.reflectoring.coderadar.graph.projectadministration.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.graph.projectadministration.analyzer.repository.SaveCommitRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveCommitAdapter implements SaveCommitPort {
  private final SaveCommitRepository saveCommitRepository;

  @Autowired
  public SaveCommitAdapter(SaveCommitRepository saveCommitRepository) {
    this.saveCommitRepository = saveCommitRepository;
  }

  @Override
  public void saveCommit(Commit commit) {
    saveCommitRepository.save(commit, 1);
  }

  @Override
  public void saveCommits(Collection<Commit> commits) {
    saveCommitRepository.saveAll(commits);
  }
}
