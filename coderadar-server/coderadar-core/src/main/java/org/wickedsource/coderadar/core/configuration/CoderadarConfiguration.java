package org.wickedsource.coderadar.core.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Provides access to all configuration parameters of the coderadar application. */
@Component
@ConfigurationProperties("coderadar")
public class CoderadarConfiguration {

	private Logger logger = LoggerFactory.getLogger(CoderadarConfiguration.class);

	private static final String CONFIG_PARAM_LOG_PATTERN = "%s is set to '%s'";

	/** Number of milliseconds that @Scheduled tasks should wait before executing again. */
	public static final int TIMER_INTERVAL = 100;

	@NotNull private Boolean master;

	@NotNull private Boolean slave;

	@NotNull private Path workdir;

	@NotNull private Integer scanIntervalInSeconds = 300;

	@NotNull private Locale dateLocale = Locale.ENGLISH;

	private Authentication authentication = new Authentication();

	public Boolean isMaster() {
		return master;
	}

	public void setMaster(Boolean master) {
		this.master = master;
	}

	public Boolean isSlave() {
		return slave;
	}

	public void setSlave(Boolean slave) {
		this.slave = slave;
	}

	public Path getWorkdir() {
		return workdir;
	}

	public void setWorkdir(Path workdir) {
		this.workdir = workdir;
	}

	public Locale getDateLocale() {
		return dateLocale;
	}

	public void setDateLocale(Locale dateLocale) {
		this.dateLocale = dateLocale;
	}

	public Integer getScanIntervalInSeconds() {
		return scanIntervalInSeconds;
	}

	public void setScanIntervalInSeconds(Integer scanIntervalInSeconds) {
		this.scanIntervalInSeconds = scanIntervalInSeconds;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

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

	public static class Authentication {

		@NotNull private Integer accessTokenDurationInMinutes = 15;

		@NotNull private Boolean enabled = Boolean.TRUE;

		@NotNull private Integer refreshTokenDurationInMinutes = 86400;

		public Integer getAccessTokenDurationInMinutes() {
			return accessTokenDurationInMinutes;
		}

		public void setAccessTokenDurationInMinutes(Integer accessTokenDurationInMinutes) {
			this.accessTokenDurationInMinutes = accessTokenDurationInMinutes;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}

		public Integer getRefreshTokenDurationInMinutes() {
			return refreshTokenDurationInMinutes;
		}

		public void setRefreshTokenDurationInMinutes(Integer refreshTokenDurationInMinutes) {
			this.refreshTokenDurationInMinutes = refreshTokenDurationInMinutes;
		}
	}
}
