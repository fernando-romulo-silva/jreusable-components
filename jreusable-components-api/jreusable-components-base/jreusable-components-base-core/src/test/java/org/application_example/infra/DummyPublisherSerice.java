package org.application_example.infra;

import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;

public class DummyPublisherSerice implements InterfaceEventPublisherSerice<String> {

    @Override
    public Future<String> publish(final String event) {
        return ConcurrentUtils.constantFuture(StringUtils.EMPTY);
    }
}
