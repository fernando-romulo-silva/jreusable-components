package org.reusablecomponent.domain;

import java.util.function.Consumer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractEntityBuilder <Id, Entity extends AbstractEntity<Id>> {

    public AbstractEntityBuilder<Id, Entity> with(@NotNull final Consumer<AbstractEntityBuilder <Id, Entity>> function) {
	function.accept(this);
	return this;
    }

    @Valid
    @NotNull
    public abstract Entity build();

}
