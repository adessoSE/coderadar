package org.wickedsource.coderadar.job.sweep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.JobLogger;
import org.wickedsource.coderadar.job.domain.ProcessingStatus;
import org.wickedsource.coderadar.job.domain.SweepJob;
import org.wickedsource.coderadar.job.domain.SweepJobRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Date;

@Service
public class SweepJobTrigger {

    private JobLogger jobLogger = new JobLogger();

    private CoderadarConfiguration config;

    private ProjectRepository projectRepository;

    private CommitRepository commitRepository;

    private SweepJobRepository sweepJobRepository;

    @Autowired
    public SweepJobTrigger(CoderadarConfiguration config, ProjectRepository projectRepository, CommitRepository commitRepository, SweepJobRepository sweepJobRepository) {
        this.config = config;
        this.projectRepository = projectRepository;
        this.commitRepository = commitRepository;
        this.sweepJobRepository = sweepJobRepository;
    }

    @Scheduled(fixedDelay = 5000)
    private void trigger() {
        if (config.isMaster()) {
            for (Project project : projectRepository.findAll()) {
                // TODO: check project's sweep strategy instead of just sweeping ALL commits
                for (Commit commit : commitRepository.findByProjectIdAndSweepedFalse(project.getId())) {
                    if (sweepJobRepository.countByCommitIdAndProcessingStatus(commit.getId(), ProcessingStatus.WAITING) == 0) {
                        SweepJob job = new SweepJob();
                        job.setQueuedDate(new Date());
                        job.setProcessingStatus(ProcessingStatus.WAITING);
                        job.setCommitId(commit.getId());
                        sweepJobRepository.save(job);
                        jobLogger.queuedNewJob(job, project);
                    }
                }
            }
        }
    }
}
