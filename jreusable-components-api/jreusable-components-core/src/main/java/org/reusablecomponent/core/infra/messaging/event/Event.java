package org.reusablecomponent.core.infra.messaging.event;

import java.time.LocalDateTime;

public class Event {

    private final LocalDateTime when;

    private final String transaction;

    private final Who who;

    private final What what;

    private final Where where;

    private final Why why;

    private final String data;
    
    // ---------------------------------------------

    private Event(final Builder builder) {
	this.when = builder.when;
	this.transaction = builder.transaction;
	this.who = builder.who;
	this.what = builder.what;
	this.where = builder.where;
	this.why = builder.why;
	this.data = builder.data;
    }
    
    public LocalDateTime getWhen() {
        return when;
    }

    public String getTransaction() {
        return transaction;
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

    public String getData() {
        return data;
    }    
    

    // ---------------------------------------------

    public static class Builder {

	public LocalDateTime when;

	public String transaction;

	public Who who;

	public What what;

	public Where where;

	public Why why;

	public String data;

	public Event build() {
	    return new Event(this);
	}
    }

    // ---------------------------------------------

    record Who(String id, String realmId, String login) {}

    record What(String fact, String desc) {}

    record Where(String serviceName, String applicationName) {}

    record Why(String reason) {}
    
}
