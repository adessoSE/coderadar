package org.wickedsource.coderadar.performance;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.restclient.CoderadarRestClient;

import java.util.*;

/**
 * Monitors the monitoring metrics REST endpoint of the coderadar application over a specified
 * duration and regularly outputs the current metric values into a CSV file for later analysis.
 */
public class MetricsMonitor {

    private static Logger logger = LoggerFactory.getLogger(MetricsMonitor.class);

    private List<String> monitoredMetrics = new ArrayList<>();

    private String logPattern = "{};";

    private Map<String, Number> maxMetricValues = new HashMap<>();

    private DateTimeFormatter dateFormat = DateTimeFormat.shortDateTime().withLocale(Locale.ENGLISH);

    /**
     * Add a metric to be monitored.
     *
     * @param metricName the name of the metric to monitor.
     */
    public void addMetric(String metricName) {
        this.monitoredMetrics.add(metricName);
        logPattern += "{};";
    }

    /**
     * Starts monitoring all metrics that have been added using {@link #addMetric(String)}.
     *
     * @param client                      the client to the coderadar REST interface
     * @param durationInSeconds           the duration in seconds defining how long to monitor the metrics.
     * @param monitoringIntervalInSeconds the interval in seconds at which to output the current metric values as a semicolon-separated line.
     *                                    The output format and target can be configured for the Logger org.wickedsource.coderadar.performance.MetricMonitor
     *                                    in the logging configuration.
     * @return a Map containing the maximum value for each metric over the whole duration of the monitoring process.
     */
    public Map<String, Number> startMonitoring(CoderadarRestClient client, int durationInSeconds, int monitoringIntervalInSeconds) throws InterruptedException {
        logHeaderLine();
        long passedSeconds = 0;
        while (passedSeconds < durationInSeconds) {
            Map<String, Number> metricsSnapshot = client.getMonitoringMetrics();
            logMetricsSnapshot(metricsSnapshot);
            updateMaxMetricValues(metricsSnapshot);
            Thread.sleep(monitoringIntervalInSeconds * 1000);
            passedSeconds += monitoringIntervalInSeconds;
        }
        return maxMetricValues;
    }

    private void logHeaderLine() {
        String headerLine = "date;";
        for (String metric : monitoredMetrics) {
            headerLine += metric + ";";
        }
        logger.info(headerLine);
    }

    private void logMetricsSnapshot(Map<String, Number> metrics) {
        List<Object> args = new ArrayList<>();
        args.add(dateFormat.print(System.currentTimeMillis()));
        for (String metric : monitoredMetrics) {
            args.add(metrics.get(metric));
        }

        logger.info(logPattern, args.toArray());
    }

    private void updateMaxMetricValues(Map<String, Number> snapshot) {
        for (String metric : monitoredMetrics) {
            Number oldValue = maxMetricValues.get(metric);
            Number newValue = snapshot.get(metric);

            if (oldValue == null) {
                oldValue = 0;
            }

            if (newValue == null) {
                newValue = 0;
            }

            maxMetricValues.put(metric, max(oldValue, newValue));
        }
    }

    private Number max(Number n1, Number n2) {
        if (n1.doubleValue() > n2.doubleValue()) {
            return n1;
        } else {
            return n2;
        }
    }


}
