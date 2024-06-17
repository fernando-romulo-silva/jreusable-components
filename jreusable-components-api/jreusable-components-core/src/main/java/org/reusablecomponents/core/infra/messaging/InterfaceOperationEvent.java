package org.reusablecomponents.core.infra.messaging;

public interface InterfaceOperationEvent {

    default String getName() {
	return this.toString();
    }
    
    default String getDescription() {
	return this.getClass().getSimpleName();
    }
}
