package org.reusablecomponents.base.security;

/**
 * 
 */
public interface InterfaceSecurityService {

    /**
     * @return
     */
    String getUserName();
    
    /**
     * @return
     */
    String getUserRealm();
    
    /**
     * @return
     */
    String getSession();
    
    /**
     * @return
     */
    String getApplication();

    /**
     * @return
     */
    String getMachineName();
    
}
