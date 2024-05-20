package org.reusablecomponent.rest.rest.query.entity.nonpaged;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetOpenApi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterfaceEntityQuerySpecificationHttp<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification, HttpResponse> {

    /**
     * @param <Response>
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    HttpResponse getBy(final Specification specification, final HttpServletRequest request, final HttpServletResponse response);

}