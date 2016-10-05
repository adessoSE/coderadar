package org.wickedsource.coderadar.core.configuration.configparams;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Configures the locale to use for calculating dates (like the number of a week in a year, which can
 * differ depending on the locale).
 */
@Component
public class DateLocaleConfigurationParameter implements ConfigurationParameter<Locale> {

    public static final String NAME = "coderadar.dateLocale";

    private Environment environment;

    @Autowired
    public DateLocaleConfigurationParameter(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Optional<Locale> getValue() {
        if (envProperty() != null) {
            try {
                return Optional.of(LocaleUtils.toLocale(envProperty()));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        } else {
            return getDefaultValue();
        }
    }

    @Override
    public Optional<Locale> getDefaultValue() {
        return Optional.of(Locale.US);
    }

    @Override
    public boolean hasFallenBackOnDefaultValue() {
        return envProperty() == null;
    }

    @Override
    public List<ParameterValidationError> validate() {
        if (envProperty() == null) {
            return Collections.emptyList();
        }
        try {
            LocaleUtils.toLocale(envProperty());
            return Collections.emptyList();
        } catch (IllegalArgumentException e) {
            return Collections.singletonList(new ParameterValidationError(String.format("'%s' is an invalid Locale. Correct format would be something like 'en_US' (language_country).", envProperty())));
        }
    }

    private String envProperty() {
        return environment.getProperty(NAME);
    }
}
