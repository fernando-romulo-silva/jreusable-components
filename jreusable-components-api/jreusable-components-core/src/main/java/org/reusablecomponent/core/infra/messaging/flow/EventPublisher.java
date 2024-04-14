package org.reusablecomponent.core.infra.messaging.flow;

import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;

import org.reusablecomponent.core.infra.messaging.event.Event;

class EventPublisher extends SubmissionPublisher<Event> {

    public EventPublisher() {
	super(Executors.newSingleThreadExecutor(), 5);
    }
}
