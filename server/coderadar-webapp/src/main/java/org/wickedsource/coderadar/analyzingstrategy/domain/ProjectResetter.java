package org.wickedsource.coderadar.analyzingstrategy.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.analyze.AnalyzeCommitJobRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;
import org.wickedsource.coderadar.metric.domain.finding.FindingRepository;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.project.domain.Project;

import java.util.Arrays;

@Component
public class ProjectResetter {

    private Logger logger = LoggerFactory.getLogger(ProjectResetter.class);

    private MetricValueRepository metricValueRepository;

    private FindingRepository findingRepository;

    private CommitRepository commitRepository;

    private AnalyzeCommitJobRepository analyzeCommitJobRepository;

    @Autowired
    public ProjectResetter(MetricValueRepository metricValueRepository, FindingRepository findingRepository, CommitRepository commitRepository, AnalyzeCommitJobRepository analyzeCommitJobRepository) {
        this.metricValueRepository = metricValueRepository;
        this.findingRepository = findingRepository;
        this.commitRepository = commitRepository;
        this.analyzeCommitJobRepository = analyzeCommitJobRepository;
    }

    /**
     * Deletes all analysis results for the given project so that analysis can be started over.
     */
    public void resetProject(Project project){
        int waitingJobCount =  analyzeCommitJobRepository.countByProjectIdAndProcessingStatusIn(project.getId(), Arrays.asList(ProcessingStatus.WAITING, ProcessingStatus.PROCESSING));
        if(waitingJobCount > 0){
            throw new ProjectResetException();
        }
        logger.debug("deleted {} MetricValue entities", metricValueRepository.deleteByProjectId(project.getId()));
        logger.debug("deleted {} Finding entities", findingRepository.deleteByProjectId(project.getId()));
        logger.debug("deleted {} AnalyzeCommitJob entities", analyzeCommitJobRepository.deleteByProjectId(project.getId()));
        commitRepository.resetAnalyzedFlagForProjectId(project.getId());
    }

}
