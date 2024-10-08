package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import org.reusablecomponents.base.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;

/**
 * Interface responsible for establishing contracts to retrieve objects using
 * specification.
 * 
 * @param <Entity>         The facade entity type
 * @param <Id>             The facade entity id type
 * 
 * @param <OneResult>      One result type
 * @param <MultipleResult> multiple result type
 * @param <CountResult>    count result type
 * @param <ExistsResult>   exists result type
 * 
 * @param <Specification>  The specification is a strutucte used to filter and
 *                         ordered queries
 */
public non-sealed interface InterfaceEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, //
        OneResult, //
        MultipleResult, //
        CountResult, //
        ExistsResult, //
        Specification> //
        extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * Find and retrieve a {@code MultipleResult} object by id
     * 
     * @param specification The query specification, how to filter
     * @param directives    Params used to configure the query's result
     * 
     * @throws NullPointerException     If the parameter 'specification' is
     *                                  null
     * @throws ElementNotFoundException If you try to retrieve an entity that
     *                                  doesn't exist
     * @throws BaseApplicationException If an unidentified error happened
     * 
     * @return Return a {@code OneResult} object
     */
    MultipleResult findBy(final Specification specification, final Object... directives);

    /**
     * Find and retrieve a {@code OneResult} object by id
     * 
     * @param specification The query specification
     * @param directives    Params used to configure the query's result
     * 
     * @throws NullPointerException     If the parameter 'specification' is
     *                                  null
     * @throws ElementNotFoundException If you try to retrieve an entity that
     *                                  doesn't exist
     * @throws BaseApplicationException If an unidentified error happened
     * 
     * @return Return a {@code OneResult} object
     */
    OneResult findOneBy(final Specification specification, final Object... directives);

    /**
     * Check if there exists an entity with the provided specification.
     * 
     * @param specification The query specification
     * 
     * @throws NullPointerException     If the parameter 'specification' is null
     * 
     * @throws BaseApplicationException If an unidentified error happened
     * 
     * @return Return a {@code ExistsResult} object
     */
    ExistsResult existsBy(final Specification specification);

    /**
     * Count how many entities there are by specification
     * 
     * @throws NullPointerException     If the parameter 'specification' is null
     * 
     * @throws BaseApplicationException If an unidentified error happened
     * 
     * @return Return a {@code CountResult} object
     */
    CountResult count(final Specification specification);

}
