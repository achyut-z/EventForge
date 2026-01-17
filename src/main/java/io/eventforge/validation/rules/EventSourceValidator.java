package io.eventforge.validation.rules;

import io.eventforge.event.Event;
import io.eventforge.validation.EventValidator;

public class EventSourceValidator implements EventValidator {

    @Override
    public void validate(Event event) {

        String source = event.getSource();

        if (source.length() < 5 || source.length() > 100) {
            throw new IllegalArgumentException(
                    "Event source identifier should be within reasonable length.");
        }

        if (source.contains(" ")) {
            throw new IllegalArgumentException("Event source must not contain whitespace");
        }

        /**
         * Here i accidentally used the OR operator to check the condition
         * but that would have been wrong because even if the source
         * was sane and valid, this would have been triggered
         * since a valid source would contain only one of the
         * namespace character in its name/value
         */
        if (!source.contains(".") && !source.contains("-") &&
                !source.contains(":") && !source.contains("/")) {
            throw new IllegalArgumentException("Event source must be namespaced identifier.");
        }

    }
}
