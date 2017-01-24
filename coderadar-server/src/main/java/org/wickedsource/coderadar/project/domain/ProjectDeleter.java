package org.wickedsource.coderadar.project.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationFileRepository;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJobRepository;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociationRepository;
import org.wickedsource.coderadar.commit.domain.ModuleAssociationRepository;
import org.wickedsource.coderadar.file.domain.CommitLogEntryRepository;
import org.wickedsource.coderadar.file.domain.FileIdentityRepository;
import org.wickedsource.coderadar.file.domain.FileRepository;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.job.core.JobRepository;
import org.wickedsource.coderadar.metric.domain.finding.FindingRepository;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.module.domain.ModuleRepository;

@Component
public class ProjectDeleter {

  private Logger logger = LoggerFactory.getLogger(ProjectDeleter.class);

  private AnalyzingJobRepository analyzingJobRepository;

  private CommitRepository commitRepository;

  private FilePatternRepository filePatternRepository;

  private ProjectRepository projectRepository;

  private JobRepository jobRepository;

  private AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository;

  private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  private CommitToFileAssociationRepository commitToFileAssociationRepository;

  private FileRepository fileRepository;

  private CommitLogEntryRepository commitLogEntryRepository;

  private FindingRepository findingRepository;

  private MetricValueRepository metricValueRepository;

  private FileIdentityRepository fileIdentityRepository;

  private ModuleRepository moduleRepository;

  private ModuleAssociationRepository moduleAssociationRepository;

  @Autowired
  public ProjectDeleter(
      AnalyzingJobRepository analyzingJobRepository,
      CommitRepository commitRepository,
      FilePatternRepository filePatternRepository,
      ProjectRepository projectRepository,
      JobRepository jobRepository,
      AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository,
      AnalyzerConfigurationRepository analyzerConfigurationRepository,
      CommitToFileAssociationRepository commitToFileAssociationRepository,
      FileRepository fileRepository,
      CommitLogEntryRepository commitLogEntryRepository,
      FindingRepository findingRepository,
      MetricValueRepository metricValueRepository,
      FileIdentityRepository fileIdentityRepository,
      ModuleRepository moduleRepository,
      ModuleAssociationRepository moduleAssociationRepository) {
    this.analyzingJobRepository = analyzingJobRepository;
    this.commitRepository = commitRepository;
    this.filePatternRepository = filePatternRepository;
    this.projectRepository = projectRepository;
    this.jobRepository = jobRepository;
    this.analyzerConfigurationFileRepository = analyzerConfigurationFileRepository;
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
    this.commitToFileAssociationRepository = commitToFileAssociationRepository;
    this.fileRepository = fileRepository;
    this.commitLogEntryRepository = commitLogEntryRepository;
    this.findingRepository = findingRepository;
    this.metricValueRepository = metricValueRepository;
    this.fileIdentityRepository = fileIdentityRepository;
    this.moduleRepository = moduleRepository;
    this.moduleAssociationRepository = moduleAssociationRepository;
  }

  public void deleteProject(Long id) {
    logger.debug(
        "deleted {} AnalyzerConfigurationFile entities",
        analyzerConfigurationFileRepository.deleteByProjectId(id));
    logger.debug(
        "deleted {} AnalyzerConfiguration entities",
        analyzerConfigurationRepository.deleteByProjectId(id));
    logger.debug("deleted {} Job entities", jobRepository.deleteByProjectId(id));
    logger.debug(
        "deleted {} AnalyzingJob entities", analyzingJobRepository.deleteByProjectId(id));
    logger.debug(
        "deleted {} CommitLogEntry entities", commitLogEntryRepository.deleteByProjectId(id));
    logger.debug("deleted {} MetricValue entities", metricValueRepository.deleteByProjectId(id));
    logger.debug("deleted {} Finding entities", findingRepository.deleteByProjectId(id));
    logger.debug(
        "deleted {} ModuleAssociation entities", moduleAssociationRepository.deleteByProjectId(id));
    logger.debug(
        "deleted {} CommitToFileAssociation entities",
        commitToFileAssociationRepository.deleteByProjectId(id));
    logger.debug("deleted {} Module entities", moduleRepository.deleteByProjectId(id));
    logger.debug("deleted {} File entities", fileRepository.deleteByProjectId(id));
    logger.debug("deleted {} FileIdentity entities", fileIdentityRepository.deleteByProjectId(id));
    logger.debug("deleted {} Commit entities", commitRepository.deleteByProjectId(id));
    logger.debug("deleted {} FilePattern entities", filePatternRepository.deleteByProjectId(id));
    logger.debug("deleted {} Project entities", projectRepository.deleteById(id));
  }
}
