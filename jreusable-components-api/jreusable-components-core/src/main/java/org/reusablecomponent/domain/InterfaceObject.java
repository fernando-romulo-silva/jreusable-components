package org.reusablecomponent.domain;

public interface InterfaceObject {

    default String toPrint() {
	return toString();
    }
    
}
