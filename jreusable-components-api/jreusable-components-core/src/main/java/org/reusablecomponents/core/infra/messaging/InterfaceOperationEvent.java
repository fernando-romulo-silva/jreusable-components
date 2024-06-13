package org.reusablecomponents.core.infra.messaging;

public interface InterfaceOperationEvent {

    default String getName() {
	return this.getClass().getSimpleName();
    }
    
    default String getDescription() {
	return this.toString();
    }
}
