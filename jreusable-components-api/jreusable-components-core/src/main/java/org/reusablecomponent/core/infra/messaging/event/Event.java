package org.reusablecomponent.core.infra.messaging.event;

import java.util.function.Consumer;

/**
 * 
 */
public class Event {

    private final When when;

    private final Who who;

    private final What what;

    private final Where where;

    private final Why why;

    // ---------------------------------------------

    private Event(final Builder builder) {
	this.when = builder.when;
	this.who = builder.who;
	this.what = builder.what;
	this.where = builder.where;
	this.why = builder.why;
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


    // ---------------------------------------------

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
