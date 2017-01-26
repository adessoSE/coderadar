package org.wickedsource.coderadar.core.configuration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.configuration.configparams.*;
import org.wickedsource.coderadar.core.configuration.configparams.authentication.AccessTokenDurationParameter;
import org.wickedsource.coderadar.core.configuration.configparams.authentication.AuthenticationEnabledParameter;
import org.wickedsource.coderadar.core.configuration.configparams.authentication.RefreshTokenDurationParameter;

/** Provides access to all configuration parameters of the coderadar application. */
@Component
public class CoderadarConfiguration {

  private Logger logger = LoggerFactory.getLogger(CoderadarConfiguration.class);

  /** Number of milliseconds that @Scheduled tasks should wait before executing again. */
  public static final int TIMER_INTERVAL = 100;

  private List<ConfigurationParameter> configurationParameters = new ArrayList<>();

  private MasterConfigurationParameter master;

  private SlaveConfigurationParameter slave;

  private WorkdirConfigurationParameter workdir;

  private ScanIntervalConfigurationParameter scanInterval;

  private DateLocaleConfigurationParameter dateLocale;

  private AccessTokenDurationParameter accessTokenDuration;

  private RefreshTokenDurationParameter refreshTokenDuration;

  private AuthenticationEnabledParameter authenticationEnabled;

  @Autowired
  public CoderadarConfiguration(
      MasterConfigurationParameter master,
      SlaveConfigurationParameter slave,
      WorkdirConfigurationParameter workdir,
      ScanIntervalConfigurationParameter scanInterval,
      DateLocaleConfigurationParameter dateLocale,
      AccessTokenDurationParameter accessTokenDuration,
      RefreshTokenDurationParameter refreshTokenDuration,
      AuthenticationEnabledParameter authenticationEnabled) {

    this.master = master;
    this.slave = slave;
    this.workdir = workdir;
    this.scanInterval = scanInterval;
    this.dateLocale = dateLocale;
    this.accessTokenDuration = accessTokenDuration;
    this.refreshTokenDuration = refreshTokenDuration;
    this.authenticationEnabled = authenticationEnabled;

    this.configurationParameters.add(master);
    this.configurationParameters.add(slave);
    this.configurationParameters.add(workdir);
    this.configurationParameters.add(scanInterval);
    this.configurationParameters.add(dateLocale);
    this.configurationParameters.add(accessTokenDuration);
    this.configurationParameters.add(refreshTokenDuration);
    this.configurationParameters.add(authenticationEnabled);
  }

  @PostConstruct
  public void checkConfiguration() {
    int errorCount = 0;
    for (ConfigurationParameter<?> param : this.configurationParameters) {
      if (!isConfigParamValid(param)) {
        errorCount++;
      }
    }
    if (errorCount > 0) {
      throw new ConfigurationException(errorCount);
    }
  }

  /**
   * Checks if a config parameter is set to a valid value and provides some log output for finding
   * configuration errors quickly.
   */
  protected boolean isConfigParamValid(ConfigurationParameter<?> param) {
    List<ParameterValidationError> validationErrors = param.validate();

    // log validation errors
    if (!validationErrors.isEmpty()) {
      for (ParameterValidationError validationError : validationErrors) {
        if (validationError.getException() == null) {
          logger.error(
              "Configuration parameter '{}' has an invalid value. Message: {}",
              param.getName(),
              validationError.getMessage());
        } else {
          logger.error(
              "Configuration parameter '{}' has an invalid value. Message: {}. Stacktrace: ",
              param.getName(),
              validationError.getMessage(),
              validationError.getException());
        }
      }
      return false;
    }

    // null check only if validation was successful
    if (!param.getValue().isPresent() && !param.getDefaultValue().isPresent()) {
      logger.error(
          "Configuration parameter '{}' is not set and has no default value!", param.getName());
      return false;
    }

    // fallback to default value
    if (param.getValue().isPresent() && param.hasFallenBackOnDefaultValue()) {
      logger.info(
          "Setting configuration parameter '{}' to default value '{}' since no value was specified.",
          param.getName(),
          param.getValue().get());
      return true;
    }

    // take specified value
    logger.info(
        "Setting configuration parameter '{}' to value '{}'.",
        param.getName(),
        param.getValue().get());
    return true;
  }

  /** @see MasterConfigurationParameter */
  public boolean isMaster() {
    return master.getValue().get();
  }

  /** @see SlaveConfigurationParameter */
  public boolean isSlave() {
    return slave.getValue().get();
  }

  /** @see WorkdirConfigurationParameter */
  public Path getWorkdir() {
    return workdir.getValue().get();
  }

  /** @see ScanIntervalConfigurationParameter */
  public int getScanIntervalInSeconds() {
    return scanInterval.getValue().get();
  }

  /** @see DateLocaleConfigurationParameter */
  public Locale getDateLocale() {
    return dateLocale.getValue().get();
  }

  /** @see AccessTokenDurationParameter */
  public Integer getAccessTokenDuration() {
    return accessTokenDuration.getValue().get();
  }

  /** @see RefreshTokenDurationParameter */
  public Integer getRefreshTokenDuration() {
    return refreshTokenDuration.getValue().get();
  }

  /** @see AuthenticationEnabledParameter */
  public Boolean isAuthenticationEnabled(){
    return authenticationEnabled.getValue().get();
  }
}
