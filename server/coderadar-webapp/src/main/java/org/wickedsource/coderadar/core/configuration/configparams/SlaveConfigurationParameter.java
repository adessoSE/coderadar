package org.wickedsource.coderadar.core.configuration.configparams;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Determines if the Coderadar application is configured as a slave node in a coderadar cluster. A
 * node can be configured as master and slave simultaneously. However, there must only be a single
 * master in a cluster.
 */
@Component
public class SlaveConfigurationParameter implements ConfigurationParameter<Boolean> {

  public static final String NAME = "coderadar.slave";

  private Environment environment;

  @Autowired
  public SlaveConfigurationParameter(Environment environment) {
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
      return Optional.empty();
    }
  }

  @Override
  public Optional<Boolean> getDefaultValue() {
    return Optional.empty();
  }

  @Override
  public boolean hasFallenBackOnDefaultValue() {
    return false;
  }

  @Override
  public List<ParameterValidationError> validate() {
    return Collections.emptyList();
  }

  private String envProperty() {
    return environment.getProperty(NAME);
  }
}
