package org.reusablecomponent.infra.messaging.event;

public class Event {

    private long when;
    
    private String transaction; 
    
    private Who who; 
    
    private What what; 
    
    private Where where; 
    
    private Why why; 
    
    private String data;
    
    private Event(final Builder builder) {
//	this.when = builder.
    }

    // ---------------------------------------------
    
    public static class Builder {
	
	public Event build() {
	   return new Event(this);
	}
    }
    
    
    record Who(String id, String realmId, String login){}
    
    record What(String fact, String desc) {}
    
    record Where(String serviceName, String applicationName) {}
    
    record Why(String reason) {}
}
