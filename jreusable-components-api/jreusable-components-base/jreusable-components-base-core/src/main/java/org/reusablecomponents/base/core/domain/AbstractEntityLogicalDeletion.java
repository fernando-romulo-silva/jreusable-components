package org.reusablecomponents.base.core.domain;

public abstract class AbstractEntityLogicalDeletion<Id> extends AbstractEntity<Id>
        implements InterfaceEntityLogicalDeletion<Id, AbstractEntityLogicalDeletion<Id>> {

    protected boolean deleted = false;

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void delete() {
        deleted = true;
    }
}
