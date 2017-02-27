package org.wickedsource.coderadar.restclient;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.wickedsource.coderadar.analyzer.rest.analyzerconfiguration.AnalyzerConfigurationResource;
import org.wickedsource.coderadar.analyzingjob.rest.AnalyzingJobResource;
import org.wickedsource.coderadar.filepattern.rest.FilePatternResource;
import org.wickedsource.coderadar.project.rest.ProjectResource;

/** A client to the coderadar REST API. */
public class CoderadarRestClient {

  private static Logger logger = LoggerFactory.getLogger(CoderadarRestClient.class);

  private String baseUrl;

  private RestTemplate restTemplate;

  /**
   * Constructor.
   *
   * @param baseUrl the base URL to the REST endpoints (e.g. "http://localhost:8080").
   */
  public CoderadarRestClient(String baseUrl) {
    this.baseUrl = baseUrl;
    this.restTemplate = new RestTemplate();
  }

  /**
   * Creates a new Project resource.
   *
   * @param project the Project resource to create.
   * @return the created Project resource.
   */
  public ProjectResource createProject(ProjectResource project) {
    String url = url("projects");
    logger.info("creating project via endpoint '{}'", url);
    return restTemplate.postForObject(url, entity(project), ProjectResource.class);
  }

  /**
   * Updates the given project's FilePattern resource.
   *
   * @param projectId ID of the project.
   * @param patterns the FilePattern resource.
   * @return the FilePattern resource.
   */
  public FilePatternResource setFilePatterns(long projectId, FilePatternResource patterns) {
    String url = projectUrl(projectId, "files");
    logger.info("adding file patterns via endpoint '{}'", url);
    return restTemplate.postForObject(url, entity(patterns), FilePatternResource.class);
  }

  /**
   * Adds an AnalyzerConfiguration resource to the given project.
   *
   * @param projectId ID of the project.
   * @param analyzer the AnalyzerConfiguration resource to add.
   * @return the added AnalyzerConfiguration resource.
   */
  public AnalyzerConfigurationResource addAnalyzerConfiguration(
      long projectId, AnalyzerConfigurationResource analyzer) {
    String url = projectUrl(projectId, "analyzers");
    logger.info("adding analyzer configuration via endpoint '{}'", url);
    return restTemplate.postForObject(url, entity(analyzer), AnalyzerConfigurationResource.class);
  }

  /**
   * Starts an AnalyzingJob for the given project.
   *
   * @param projectId ID of the project.
   * @param job The AnalyzingJob to start.
   * @return the started AnalyzingJob.
   */
  public AnalyzingJobResource addAnalyzingJob(long projectId, AnalyzingJobResource job) {
    String url = projectUrl(projectId, "analyzingJob");
    logger.info("starting analyzing job via endpoint '{}'", url);
    return restTemplate.postForObject(url, entity(job), AnalyzingJobResource.class);
  }

  /**
   * Retrieves coderadar's monitoring metrics from the monitoring endpoint.
   *
   * @return Map with the metric name as key and a numeric value.
   */
  public Map<String, Number> getMonitoringMetrics() {
    String url = url("metrics");
    logger.info("loading monitoring metrics via endpoint '{}'", url);
    return restTemplate.getForObject(url, Map.class);
  }

  private String url(String relativeUrl) {
    return String.format("%s/%s", this.baseUrl, relativeUrl);
  }

  private String projectUrl(long projectId, String relativeUrl) {
    return url(String.format("projects/%d/%s", projectId, relativeUrl));
  }

  private <T> HttpEntity<T> entity(T payload) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity(payload, headers);
  }
}
