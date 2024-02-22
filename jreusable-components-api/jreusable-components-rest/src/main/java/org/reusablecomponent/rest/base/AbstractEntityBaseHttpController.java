package org.reusablecomponent.rest.base;

/**
 * @param <Id>
 * @param <OneResult>
 */
public abstract class AbstractEntityBaseHttpController <Id, OneResult> implements InterfaceEntityBaseHttpController {

    /**
     * @param id
     * @return
     */
    protected abstract OneResult findById(final Id id);

}
