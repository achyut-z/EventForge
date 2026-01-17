package io.eventforge.validation.rules;

import io.eventforge.event.Event;
import io.eventforge.validation.EventValidator;

public class EventTypeValidator implements EventValidator {

    @Override
    public void validate(Event event) {

        String type = event.getType();

        if (!type.contains(".")) {
            throw new IllegalArgumentException("Event type must follow the format <domain>.<action>");
        }

        if (type.startsWith(".") || type.endsWith(".")) {
            throw new IllegalArgumentException("Event type cannot start or end with a dot.");
        }

    }
}
