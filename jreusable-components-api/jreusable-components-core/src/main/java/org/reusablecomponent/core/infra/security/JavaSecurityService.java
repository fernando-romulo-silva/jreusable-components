package org.reusablecomponent.core.infra.security;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;

public class JavaSecurityService implements InterfaceSecurityService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserName() {
	return System.getProperty("user.name");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserRealm() {
	try {
	    final var addr = InetAddress.getLocalHost();
	    return addr.getHostName();
	} catch (final UnknownHostException ex) {
	    return StringUtils.EMPTY;
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSession() {
	return "NOSESSION";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplication() {
	return "NOAPPLICATION";
    }
}
