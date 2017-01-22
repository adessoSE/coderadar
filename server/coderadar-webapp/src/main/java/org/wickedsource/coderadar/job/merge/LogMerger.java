package org.wickedsource.coderadar.job.merge;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

@Service
public class LogMerger {

  private Logger logger = LoggerFactory.getLogger(LogMerger.class);

  private CommitRepository commitRepository;

  private ProjectRepository projectRepository;

  private CommitLogMerger commitLogMerger;

  @Autowired
  public LogMerger(
      CommitRepository commitRepository,
      ProjectRepository projectRepository,
      CommitLogMerger commitLogMerger) {
    this.commitRepository = commitRepository;
    this.projectRepository = projectRepository;
    this.commitLogMerger = commitLogMerger;
  }

  public void merge(Project project) {
    List<Commit> commitsToMerge =
        commitRepository.findByProjectIdAndScannedTrueAndMergedFalseOrderByTimestamp(
            project.getId());
    for (Commit commit : commitsToMerge) {
      commitLogMerger.mergeCommit(commit);
    }
    logger.info("processed {} commits for project {}", commitsToMerge.size(), project);
  }
}
