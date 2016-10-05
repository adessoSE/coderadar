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
     * Returns the value of the configuration parameter. If no value is explicitly set, may return a default value (in this case
     * hasDefaultValue() and hasFallenBackOnDefaultValue() must both return true!). If no value is available,
     * must return an empty Optional.
     */
    Optional<T> getValue();

    /**
     * If this configuration parameter has a default value, return it. Otherwise return an empty Optional.
     */
    Optional<T> getDefaultValue();

    /**
     * Returns true if no specific value has been set for this configuration parameter and thus getValue() returns the
     * default value. If this method returns true, getDefaultValue() must not return an empty Optional!
     */
    boolean hasFallenBackOnDefaultValue();

    /**
     * Validates the configuration parameter value. Returns a {@link ParameterValidationError} object for each
     * issue with the parameter value. The error messages are logged at ERROR level so the user
     * can quickly see what's wrong and thus should contain meaningful error messages.
     * <p/>
     * This validation does not need to check on
     */
    List<ParameterValidationError> validate();

}
