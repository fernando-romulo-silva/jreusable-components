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
}
