package org.reusablecomponents.rest.rest.query.entity.base;

import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQueryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EntityQueryBaseHttpController<QueryIdIn, ExistsResult, OneResult, HttpResponseVoid, HttpResponseOne>
        implements InterfaceEntityQueryBaseController<QueryIdIn, HttpResponseVoid, HttpResponseOne> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryBaseHttpController.class);

    private final InterfaceQueryFacade<?, ?, QueryIdIn, OneResult, ?, ?, ExistsResult> entityQueryFacade;

    protected final Function<OneResult, HttpResponseOne> createResponseGetOneFunction;

    protected final Function<ExistsResult, HttpResponseVoid> createResponseHeadFunction;

    protected EntityQueryBaseHttpController(
            final InterfaceQueryFacade<?, ?, QueryIdIn, OneResult, ?, ?, ExistsResult> entityQueryFacade,
            final Function<OneResult, HttpResponseOne> createResponseGetOneFunction,
            final Function<ExistsResult, HttpResponseVoid> createResponseHeadFunction) {
        super();
        this.entityQueryFacade = entityQueryFacade;
        this.createResponseGetOneFunction = createResponseGetOneFunction;
        this.createResponseHeadFunction = createResponseHeadFunction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponseOne get(final QueryIdIn queryIdIn, final HttpServletRequest request,
            final HttpServletResponse response) {

        LOGGER.debug("Geting entity by '{}'", queryIdIn);

        final var findByResult = entityQueryFacade.findById(queryIdIn);

        final var finalResult = createResponseGetOneFunction.apply(findByResult);

        LOGGER.debug("Got entity by '{}', result '{}'", queryIdIn, finalResult);

        return finalResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponseVoid head(final QueryIdIn queryIdIn, final HttpServletRequest request,
            final HttpServletResponse response) {

        LOGGER.debug("Check entity by '{}'", queryIdIn);

        final var existsByResult = entityQueryFacade.existsById(queryIdIn);

        final var finalResult = createResponseHeadFunction.apply(existsByResult);

        LOGGER.debug("Checked entity by '{}', result '{}'", queryIdIn, finalResult);

        return finalResult;
    }
}
