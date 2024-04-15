package org.reusablecomponent.core.infra.security.dummy;

import org.reusablecomponent.core.infra.security.InterfaceSecurityService;

public class DummySecurityService implements InterfaceSecurityService {

    @Override
    public String getUserName() {
	return "DummyUser";
    }

    @Override
    public String getUserRealm() {
	return "DummyRealm";
    }

    @Override
    public String getApplication() {
	return "DummyApplication";
    }

    @Override
    public String getSession() {
	return "550e8400-e29b-41d4-a716-446655440000";
    }
}
