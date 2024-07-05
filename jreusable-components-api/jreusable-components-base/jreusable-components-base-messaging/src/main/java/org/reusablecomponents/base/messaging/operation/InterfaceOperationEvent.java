package org.reusablecomponents.base.messaging.operation;

public interface InterfaceOperationEvent {

    default String getName() {
	return this.toString();
    }
    
    default String getDescription() {
	return this.getClass().getSimpleName();
    }
}
