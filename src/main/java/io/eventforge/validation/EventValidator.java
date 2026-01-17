package io.eventforge.validation;

import io.eventforge.event.Event;

public interface EventValidator {

    void validate(Event event);
}
