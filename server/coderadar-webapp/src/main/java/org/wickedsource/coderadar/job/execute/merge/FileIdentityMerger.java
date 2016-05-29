package org.wickedsource.coderadar.job.execute.merge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.List;

@Service
public class FileIdentityMerger {

    private Logger logger = LoggerFactory.getLogger(FileIdentityMerger.class);

    private CommitRepository commitRepository;

    private ProjectRepository projectRepository;

    private CommitMerger commitMerger;

    @Autowired
    public FileIdentityMerger(CommitRepository commitRepository, ProjectRepository projectRepository, CommitMerger commitMerger) {
        this.commitRepository = commitRepository;
        this.projectRepository = projectRepository;
        this.commitMerger = commitMerger;
    }

    public void merge(Long projectId) {
        Project project = projectRepository.findOne(projectId);
        List<Commit> commitsToMerge = commitRepository.findByProjectIdAndScannedTrueAndMergedFalseOrderByTimestamp(project.getId());
        for (Commit commit : commitsToMerge) {
            commitMerger.mergeCommit(commit);

        }
        logger.info("processed {} commits for project {}", commitsToMerge.size(), project);
    }

}
