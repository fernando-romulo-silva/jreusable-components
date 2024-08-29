package org.reusablecomponents.base.security;

/**
 * Service in charge for security matters.
 */
public interface InterfaceSecurityService {

    /**
     * Return the logged user name.
     * 
     * @return An String object
     */
    String getUserName();

    /**
     * Return the logged user realm.
     * 
     * @return An String object
     */
    String getUserRealm();

    /**
     * Return the logged user current session id.
     * 
     * @return An String object
     */
    String getSession();

    /**
     * Return the application name.
     * 
     * @return An String object
     */
    String getApplication();

    /**
     * Return the machine name.
     * 
     * @return An String object
     */
    String getMachineName();

}
