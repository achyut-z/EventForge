package io.eventforge.validation;

import io.eventforge.event.Event;

import java.util.List;

public class EventValidatorPipeline {

    private final List<EventValidator> eventValidators;

    public EventValidatorPipeline(List<EventValidator> eventValidators) {

        this.eventValidators = eventValidators;
    }

    public void validate(Event event) {

        for(EventValidator validators : eventValidators) {
            validators.validate(event);
        }
    }
}
