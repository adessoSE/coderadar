package org.wickedsource.coderadar.core.configuration.configparams.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.configuration.configparams.ConfigurationParameter;
import org.wickedsource.coderadar.core.configuration.configparams.ParameterValidationError;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** Configures the number of minutes the authentication refresh token is valid. */
@Component
public class RefreshTokenDurationParameter implements ConfigurationParameter<Integer> {

  public static final String NAME = "coderadar.authentication.refreshToken.durationInMinutes";

  private Environment environment;

  @Autowired
  public RefreshTokenDurationParameter(Environment environment) {
    this.environment = environment;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public Optional<Integer> getValue() {
    if (envProperty() != null) {
      return Optional.of(Integer.valueOf(envProperty()));
    } else {
      return getDefaultValue();
    }
  }

  @Override
  public Optional<Integer> getDefaultValue() {
    return Optional.of(86400);
  }

  @Override
  public boolean hasFallenBackOnDefaultValue() {
    return envProperty() == null;
  }

  @Override
  public List<ParameterValidationError> validate() {
    if (envProperty() != null && !envProperty().matches("^[0-9]+$")) {
      return Collections.singletonList(
          new ParameterValidationError(
              String.format("'%s' is not a valid integer!", envProperty())));
    }
    return Collections.emptyList();
  }

  private String envProperty() {
    return environment.getProperty(NAME);
  }
}
