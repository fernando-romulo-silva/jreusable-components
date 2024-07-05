package org.reusablecomponents.rest.rest.full.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;

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
