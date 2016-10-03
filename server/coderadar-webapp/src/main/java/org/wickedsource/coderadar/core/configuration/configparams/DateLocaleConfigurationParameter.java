package org.wickedsource.coderadar.core.configuration.configparams;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${coderadar.dateLocale:}")
    private Optional<String> value;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Locale getValue() {
        if (value.isPresent()) {
            return LocaleUtils.toLocale(value.get());
        } else {
            return getDefaultValue().get();
        }
    }

    @Override
    public Optional<Locale> getDefaultValue() {
        return Optional.of(Locale.US);
    }

    @Override
    public List<ParameterValidationError> validate() {
        try {
            LocaleUtils.toLocale(value.get());
            return Collections.emptyList();
        } catch (IllegalArgumentException e) {
            return Collections.singletonList(new ParameterValidationError(String.format("'%s' is an invalid Locale. Correct format would be something like 'en_us' (language_country).", this.value)));
        }
    }
}
