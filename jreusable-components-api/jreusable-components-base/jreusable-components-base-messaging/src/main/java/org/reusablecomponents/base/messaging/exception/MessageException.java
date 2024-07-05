package org.reusablecomponents.base.messaging.exception;

public sealed interface MessageException permits DeadLetterException, RetryableException, RequeableException, DroppableException {

}
