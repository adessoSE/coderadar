package org.wickedsource.coderadar.core.configuration.configparams.authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.configuration.configparams.ConfigurationParameter;
import org.wickedsource.coderadar.core.configuration.configparams.ParameterValidationError;

/**
 * By setting this parameter you can enable or disable authentication for coderadar requests.
 * Default value is enabled. This configuration parameter should obviously only be used in testing
 * environments!
 */
@Component
public class AuthenticationEnabledParameter implements ConfigurationParameter<Boolean> {

  public static final String NAME = "coderadar.authentication.enabled";

  private Environment environment;

  @Autowired
  public AuthenticationEnabledParameter(Environment environment) {
    this.environment = environment;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public Optional<Boolean> getValue() {
    if (envProperty() != null) {
      return Optional.of(Boolean.valueOf(envProperty()));
    } else {
      return getDefaultValue();
    }
  }

  @Override
  public Optional<Boolean> getDefaultValue() {
    return Optional.of(Boolean.TRUE);
  }

  @Override
  public boolean hasFallenBackOnDefaultValue() {
    return envProperty() == null;
  }

  @Override
  public List<ParameterValidationError> validate() {
    return Collections.emptyList();
  }

  private String envProperty() {
    return environment.getProperty(NAME);
  }
}
