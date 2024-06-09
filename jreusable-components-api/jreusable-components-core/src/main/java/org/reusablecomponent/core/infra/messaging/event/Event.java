package org.reusablecomponent.core.infra.messaging.event;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class Event {

    private final String id;

    @Valid
    @NotNull
    private final When when;

    @Valid
    @NotNull
    private final Who who;

    @Valid
    @NotNull
    private final What what;

    @Valid
    @NotNull
    private final Where where;

    @Valid
    @NotNull
    private final Why why;

    // ---------------------------------------------

    private Event(final Builder builder) {
	this.id = UUID.randomUUID().toString();
	this.when = builder.when;
	this.who = builder.who;
	this.what = builder.what;
	this.where = builder.where;
	this.why = builder.why;
    }

    public String getId() {
	return id;
    }

    public When getWhen() {
	return when;
    }

    public Who getWho() {
	return who;
    }

    public What getWhat() {
	return what;
    }

    public Where getWhere() {
	return where;
    }

    public Why getWhy() {
	return why;
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

    // ---------------------------------------------

    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    @Override
    public boolean equals(final Object obj) {

	if (Objects.isNull(obj)) {
	    return false;
	}

	if (this == obj) {
	    return true;
	}

	if (obj instanceof Event other) {
	    return Objects.equals(id, other.id);
	}

	return false;
    }

    public static class Builder {

	public When when;

	public Who who;

	public What what;

	public Where where;

	public Why why;

	public Builder with(final Consumer<Builder> function) {
	    function.accept(this);
	    return this;
	}

	public Event build() {
	    return new Event(this);
	}
    }

}
