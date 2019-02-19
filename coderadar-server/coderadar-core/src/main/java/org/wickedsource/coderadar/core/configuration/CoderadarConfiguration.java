package org.wickedsource.coderadar.core.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Provides access to all configuration parameters of the coderadar application. */
@Component
@ConfigurationProperties("coderadar")
@Data
public class CoderadarConfiguration {

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Logger logger = LoggerFactory.getLogger(CoderadarConfiguration.class);

  private static final String CONFIG_PARAM_LOG_PATTERN = "%s is set to '%s'";

  /** Number of milliseconds that @Scheduled tasks should wait before executing again. */
  public static final int TIMER_INTERVAL = 100;

  @NotNull private boolean master;

  @NotNull private boolean slave;

  @NotNull private Path workdir;

  @NotNull private Integer scanIntervalInSeconds = 300;

  @NotNull private Locale dateLocale = Locale.ENGLISH;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Authentication authentication = new Authentication();

  @PostConstruct
  public void logConfig() {
    logger.info(String.format(CONFIG_PARAM_LOG_PATTERN, "coderadar.master", this.master));
    logger.info(String.format(CONFIG_PARAM_LOG_PATTERN, "coderadar.slave", this.slave));
    logger.info(String.format(CONFIG_PARAM_LOG_PATTERN, "coderadar.workdir", this.workdir));
    logger.info(String.format(CONFIG_PARAM_LOG_PATTERN, "coderadar.dateLocale", this.dateLocale));
    logger.info(
        String.format(
            CONFIG_PARAM_LOG_PATTERN,
            "coderadar.scanIntervalInSeconds",
            this.scanIntervalInSeconds));
    logger.info(
        String.format(
            CONFIG_PARAM_LOG_PATTERN,
            "coderadar.authentication.accessTokenDurationInMinutes",
            this.authentication.accessTokenDurationInMinutes));
    logger.info(
        String.format(
            CONFIG_PARAM_LOG_PATTERN,
            "coderadar.authentication.refreshTokenDurationInMinutes",
            this.authentication.refreshTokenDurationInMinutes));
    logger.info(
        String.format(
            CONFIG_PARAM_LOG_PATTERN,
            "coderadar.authentication.enabled",
            this.authentication.enabled));
  }

  @PostConstruct
  public void validateWorkdirIsWritable() {
    if (!Files.exists(this.workdir)) {
      try {
        Files.createDirectories(this.workdir);
      } catch (IOException e) {
        throw new IllegalArgumentException(
            String.format("directory %s could not be created!", this.workdir), e);
      }
    }

    if (!Files.isWritable(this.workdir)) {
      throw new IllegalArgumentException(
          String.format("directory %s is not writable!", this.workdir));
    }
  }

  @Data
  public static class Authentication {

    @NotNull private Integer accessTokenDurationInMinutes = 15;

    @NotNull private Boolean enabled = Boolean.TRUE;

    @NotNull private Integer refreshTokenDurationInMinutes = 86400;
  }
}
