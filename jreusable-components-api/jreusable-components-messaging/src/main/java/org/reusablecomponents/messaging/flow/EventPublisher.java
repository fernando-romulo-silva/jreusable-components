package org.reusablecomponents.messaging.flow;

import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;

class EventPublisher extends SubmissionPublisher<String> {

    public EventPublisher() {
	super(Executors.newSingleThreadExecutor(), 5);
    }
}
