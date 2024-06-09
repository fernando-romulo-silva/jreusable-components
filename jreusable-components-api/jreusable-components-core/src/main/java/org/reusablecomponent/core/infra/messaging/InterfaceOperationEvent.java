package org.reusablecomponent.core.infra.messaging;

public interface InterfaceOperationEvent {

    default String getName() {
	return this.toString();
    }
    
    default String getDescription() {
	return "NODESCRIPTION";
    }
}
