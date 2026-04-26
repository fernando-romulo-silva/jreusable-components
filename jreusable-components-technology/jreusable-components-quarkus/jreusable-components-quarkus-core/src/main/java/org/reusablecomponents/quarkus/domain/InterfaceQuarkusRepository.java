package org.reusablecomponents.quarkus.domain;

import org.reusablecomponents.base.core.domain.InterfaceEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceQuarkusRepository<Entity extends InterfaceEntity<Id>, Id>
		extends PanacheRepositoryBase<Entity, Id> {

}
