package org.reusablecomponent.application.command.entity;

import org.reusablecomponent.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <VoidResult>
 */
public interface InterfaceEntityCommandFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, VoidResult> 
	extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param entity
     * @return
     */
    OneResult save(final Entity entity);
    
    /**
     * @param entities
     * @return
     */
    MultipleResult saveAll(final Iterable<Entity> entities);    

    /**
     * @param entity
     * @return
     */
    OneResult update(final Entity entity);
    
    /**
     * @param id
     * @param entity
     * @return
     */
    OneResult update(final Id id, final Entity entity);
    
    /**
     * @param entities
     * @return
     */
    MultipleResult updateAll(final Iterable<Entity> entities);    

    /**
     * @param entity
     * @return
     */
    VoidResult delete(final Entity entity);

    /**
     * @param entities
     */
    VoidResult deleteAll(final Iterable<Entity> entities);    
    
    /**
     * @param id
     */
    VoidResult deleteBy(final Id id);
    
    /**
     * @param ids
     */
    VoidResult deleteAllBy(final Iterable<Id> ids);

}