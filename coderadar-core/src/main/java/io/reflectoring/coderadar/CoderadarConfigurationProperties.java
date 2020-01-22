package io.reflectoring.coderadar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Provides access to all configuration parameters of the coderadar application. */
@Component
@ConfigurationProperties("coderadar")
@Data
public class CoderadarConfigurationProperties {

  private static final Logger logger =
      LoggerFactory.getLogger(CoderadarConfigurationProperties.class);

  private static final String CONFIG_PARAM_LOG_PATTERN = "{} is set to '{}'";

  /** Number of milliseconds that @Scheduled tasks should wait before executing again. */
  public static final int TIMER_INTERVAL = 100;

  @NotNull private boolean master;

  @NotNull private boolean slave;

  @NotNull private Path workdir;

  @NotNull private Integer scanIntervalInSeconds = 30;

  @NotNull private Locale dateLocale = Locale.ENGLISH;

  private Authentication authentication = new Authentication();

  @PostConstruct
  public void logConfig() {
    logger.info(CONFIG_PARAM_LOG_PATTERN, "coderadar.master", this.master);
    logger.info(CONFIG_PARAM_LOG_PATTERN, "coderadar.slave", this.slave);
    logger.info(CONFIG_PARAM_LOG_PATTERN, "coderadar.workdir", this.workdir);
    logger.info(CONFIG_PARAM_LOG_PATTERN, "coderadar.dateLocale", this.dateLocale);
    logger.info(
        CONFIG_PARAM_LOG_PATTERN, "coderadar.scanIntervalInSeconds", this.scanIntervalInSeconds);
    logger.info(
        CONFIG_PARAM_LOG_PATTERN,
        "coderadar.authentication.accessTokenDurationInMinutes",
        this.authentication.accessTokenDurationInMinutes);
    logger.info(
        CONFIG_PARAM_LOG_PATTERN,
        "coderadar.authentication.refreshTokenDurationInMinutes",
        this.authentication.refreshTokenDurationInMinutes);
    logger.info(
        CONFIG_PARAM_LOG_PATTERN, "coderadar.authentication.enabled", this.authentication.enabled);
  }

  @PostConstruct
  public void validateWorkdirIsWritable() {
    if (!this.workdir.toFile().exists()) {
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
