package org.reusablecomponents.spring.core.infra.security;

import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityService implements InterfaceSecurityService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserName() {
        return "DummyUser";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserRealm() {
        return "DummyRealm";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplication() {
        return "DummyApplication";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSession() {
        return "550e8400-e29b-41d4-a716-446655440000";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMachineName() {
        return "DummyMachine";
    }
}
