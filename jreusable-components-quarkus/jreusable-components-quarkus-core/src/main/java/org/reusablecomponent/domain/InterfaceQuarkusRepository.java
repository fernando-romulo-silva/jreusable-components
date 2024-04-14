package org.reusablecomponent.domain;

import org.reusablecomponent.core.domain.AbstractEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceQuarkusRepository <Entity extends AbstractEntity<Id>, Id>  
	extends PanacheRepositoryBase<Entity, Id> {

}
