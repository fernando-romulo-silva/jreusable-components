package org.reusablecomponents.base.security;

/**
 * Interface service in charge for security matters. <br />
 * Implementations should use its technology to retrieve information about:
 * 
 * <ul>
 * <li>User name</li>
 * <li>User realm</li>
 * <li>Session</li>
 * <li>Machine name</li>
 * <li>Application</li>
 * <li>Version</li>
 * </ul>
 * 
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
     * Return the machine name.
     * 
     * @return An String object
     */
    String getMachineName();

    /**
     * Return the application name.
     * 
     * @return An String object
     */
    String getApplication();

    /**
     * Return the application version.
     * 
     * @return An String object
     */
    default String getVersion() {
        return "0.0.0";
    }

}
