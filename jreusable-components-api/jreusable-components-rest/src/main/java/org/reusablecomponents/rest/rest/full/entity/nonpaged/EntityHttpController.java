package org.reusablecomponents.rest.rest.full.entity.nonpaged;

import org.reusablecomponents.core.domain.AbstractEntity;
import org.reusablecomponents.rest.rest.command.InterfaceEntityCommandHttpController;
import org.reusablecomponents.rest.rest.full.entity.EntityCommonHttpController;
import org.reusablecomponents.rest.rest.query.entity.nonpaged.InterfaceEntityQueryHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <HttpResponse>
 */
public class EntityHttpController <Entity extends AbstractEntity<Id>, Id, HttpResponse> {
//	extends EntityCommonHttpController<Entity, Id, HttpResponse> 
//        implements InterfaceEntityQueryHttpController <Id,HttpResponse> {
//
//    final InterfaceEntityQueryHttpController<Id, HttpResponse> interfaceEntityQueryHttpController;
//    
//    /**
//     * @param interfaceEntityCommandHttpController
//     * @param interfaceEntityQueryHttpController
//     */
//    protected EntityHttpController(
//		    @NotNull final InterfaceEntityCommandHttpController<Entity, Id, HttpResponse> interfaceEntityCommandHttpController,
//		    @NotNull final InterfaceEntityQueryHttpController<Id, HttpResponse> interfaceEntityQueryHttpController) {
//	super(interfaceEntityCommandHttpController);
//	this.interfaceEntityQueryHttpController = interfaceEntityQueryHttpController;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public HttpResponse get(final Id id, final HttpServletRequest request, final HttpServletResponse response) {
//	return interfaceEntityQueryHttpController.get(id, request, response);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public HttpResponse getAll(final HttpServletRequest request, final HttpServletResponse response) {
//	return interfaceEntityQueryHttpController.getAll(request, response);
//    }
}
