package org.wickedsource.coderadar.core.configuration.configparams;

import java.util.List;
import java.util.Optional;

/**
 * Interface that must be implemented for all configuration parameters for the coderadar application.
 *
 * @param <T> the type of the configuration parameter.
 */
public interface ConfigurationParameter<T> {

    /**
     * Returns the name of the configuration parameter. This parameter can be configured externally, as
     * described at http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html.
     */
    String getName();

    /**
     * Returns the value of the configuration parameter. Must return null if the value for this configuration parameter is not set.
     * Must not return the default value in this case!
     */
    T getValue();

    /**
     * Returns the default value for this configuration parameter. An empty Optional result is interpreted as "no default value present" and
     * application startup will fail if no explicit parameter value is defined.
     */
    Optional<T> getDefaultValue();

    /**
     * Validates the configuration parameter value. Returns a {@link ParameterValidationError} object for each
     * issue with the parameter value. The error messages are logged at ERROR level so the user
     * can quickly see what's wrong and thus should contain meaningful error messages.
     * <p/>
     * This validation does not need to check on
     */
    List<ParameterValidationError> validate();

}
