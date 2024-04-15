package org.reusablecomponent.core.infra.security;

public interface InterfaceSecurityService {

    String getUserName();
    
    String getUserRealm();
    
    String getSession();
    
    String getApplication();
    
}
