package org.wickedsource.coderadar.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.rest.analyzerconfiguration.AnalyzerConfigurationResource;
import org.wickedsource.coderadar.analyzingjob.rest.AnalyzingJobResource;
import org.wickedsource.coderadar.filepattern.domain.FileSetType;
import org.wickedsource.coderadar.filepattern.rest.FilePatternDTO;
import org.wickedsource.coderadar.filepattern.rest.FilePatternResource;
import org.wickedsource.coderadar.project.domain.InclusionType;
import org.wickedsource.coderadar.project.domain.VcsType;
import org.wickedsource.coderadar.project.rest.ProjectResource;
import org.wickedsource.coderadar.restclient.CoderadarRestClient;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JobPerformanceTestRunner {

    private static final List<String> MONITORED_METRICS = Arrays.asList(
            "coderadar.CommitAnalyzer.commits.oneMinuteRate",
            "coderadar.CommitAnalyzer.files.oneMinuteRate",
            "coderadar.CommitMetaDataScanner.commits.oneMinuteRate",
            "coderadar.CommitToFileAssociator.commits.oneMinuteRate",
            "coderadar.CommitToFileAssociator.files.oneMinuteRate",
            "coderadar.FileMetadataScanner.commits.oneMinuteRate",
            "coderadar.FileMetadataScanner.files.oneMinuteRate"
    );

    private final static int DURATION_IN_SECONDS = 600;

    private final static int MONITORING_INTERVAL_IN_SECONDS = 5;

    private static Logger logger = LoggerFactory.getLogger(JobPerformanceTestRunner.class);

    public static void main(String[] args) throws InterruptedException {
        CoderadarRestClient client = new CoderadarRestClient("http://localhost:8080");

        createProject(client);
        addFilePatterns(client);
        addAnalyzerConfigurations(client);
        addAnalyzingJob(client);

        MetricsMonitor monitor = new MetricsMonitor();
        for(String metric : MONITORED_METRICS) {
            monitor.addMetric(metric);
        }

        logger.info("monitoring performance metrics every {} seconds for a duration of {} seconds ... be patient", MONITORING_INTERVAL_IN_SECONDS, DURATION_IN_SECONDS);
        Map<String, Number> maxMetrics = monitor.startMonitoring(client, DURATION_IN_SECONDS, MONITORING_INTERVAL_IN_SECONDS);
        logger.info("performance test concluded ... see results in 'metrics_report.csv'");
        logger.info("max metric values:");
        for(String metric : maxMetrics.keySet()) {
            logger.info(String.format("%-60s: %f", metric, maxMetrics.get(metric)));
        }

    }

    private static ProjectResource createProject(CoderadarRestClient client) {
        ProjectResource projectResource = new ProjectResource();
        projectResource.setVcsType(VcsType.GIT);
        projectResource.setName("coderadar");
        projectResource.setVcsUrl("https://github.com/reflectoring/coderadar.git");
        return client.createProject(projectResource);
    }

    private static FilePatternResource addFilePatterns(CoderadarRestClient client){
        FilePatternResource patterns = new FilePatternResource();
        FilePatternDTO pattern = new FilePatternDTO();
        pattern.setInclusionType(InclusionType.INCLUDE);
        pattern.setFileSetType(FileSetType.SOURCE);
        pattern.setPattern("**/src/main/java/**/*.java");
        patterns.addFilePattern(pattern);
        return client.setFilePatterns(1L, patterns);
    }

    private static void addAnalyzerConfigurations(CoderadarRestClient client){
        client.addAnalyzerConfiguration(1L, new AnalyzerConfigurationResource("org.wickedsource.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", true));
        client.addAnalyzerConfiguration(1L, new AnalyzerConfigurationResource("org.wickedsource.coderadar.analyzer.loc.LocAnalyzerPlugin", true));
    }

    private static AnalyzingJobResource addAnalyzingJob(CoderadarRestClient client){
        AnalyzingJobResource job = new AnalyzingJobResource();
        job.setActive(true);
        job.setFromDate(new Date(0));
        job.setRescan(true);
        return client.addAnalyzingJob(1L, job);
    }

}
