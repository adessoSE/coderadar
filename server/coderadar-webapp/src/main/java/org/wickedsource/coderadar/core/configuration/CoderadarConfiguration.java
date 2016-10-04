package org.wickedsource.coderadar.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.configuration.configparams.*;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Provides access to all configuration parameters of the coderadar application.
 */
@Component
public class CoderadarConfiguration {

    private Logger logger = LoggerFactory.getLogger(CoderadarConfiguration.class);

    /**
     * Number of milliseconds that @Scheduled tasks should wait before executing again.
     */
    public static final int TIMER_INTERVAL = 100;

    private List<ConfigurationParameter> configurationParameters = new ArrayList<>();

    private MasterConfigurationParameter master;

    private SlaveConfigurationParameter slave;

    private WorkdirConfigurationParameter workdir;

    private ScanIntervalConfigurationParameter scanInterval;

    private DateLocaleConfigurationParameter dateLocale;

    @Autowired
    public CoderadarConfiguration(MasterConfigurationParameter master, SlaveConfigurationParameter slave, WorkdirConfigurationParameter workdir, ScanIntervalConfigurationParameter scanInterval, DateLocaleConfigurationParameter dateLocale) {
        this.master = master;
        this.slave = slave;
        this.workdir = workdir;
        this.scanInterval = scanInterval;
        this.dateLocale = dateLocale;
        this.configurationParameters.add(master);
        this.configurationParameters.add(slave);
        this.configurationParameters.add(workdir);
        this.configurationParameters.add(scanInterval);
        this.configurationParameters.add(dateLocale);
    }

    @PostConstruct
    public void checkConfiguration() {
        int errorCount = 0;
        for (ConfigurationParameter<?> param : this.configurationParameters) {
            List<ParameterValidationError> validationErrors = param.validate();

            // log validation errors
            if (!validationErrors.isEmpty()) {
                for (ParameterValidationError validationError : validationErrors) {
                    if (validationError.getException() == null) {
                        logger.error("Configuration parameter '{}' has an invalid value. Message: {}", param.getName(), validationError.getMessage());
                    } else {
                        logger.error("Configuration parameter '{}' has an invalid value. Message: {}. Stacktrace: ", param.getName(), validationError.getMessage(), validationError.getException());
                    }
                    errorCount++;
                }
                continue;
            }

            // null check only if validation was successful
            if (param.getValue() == null && !param.getDefaultValue().isPresent()) {
                logger.error("Configuration parameter '{}' is not set and has no default value!", param.getName());
                errorCount++;
                continue;
            }

            // fallback to default value
            if (param.getValue() == null && param.getDefaultValue().isPresent()) {
                logger.info("Setting configuration parameter '{}' to default value '{}' since no value was specified.", param.getName(), param.getDefaultValue().get());
                continue;
            }

            // take specified value
            logger.info("Setting configuration parameter '{}' to value '{}'.", param.getName(), param.getValue());
        }
        if (errorCount > 0) {
            throw new ConfigurationException(errorCount);
        }

    }


    /**
     * @see MasterConfigurationParameter
     */
    public boolean isMaster() {
        return master.getValue();
    }

    /**
     * @see SlaveConfigurationParameter
     */
    public boolean isSlave() {
        return slave.getValue();
    }


    /**
     * @see WorkdirConfigurationParameter
     */
    public Path getWorkdir() {
        return workdir.getValue();
    }

    /**
     * @see ScanIntervalConfigurationParameter
     */
    public int getScanIntervalInSeconds() {
        return scanInterval.getValue();
    }

    /**
     * @see DateLocaleConfigurationParameter
     */
    public Locale getDateLocale() {
        return dateLocale.getValue();
    }

}
