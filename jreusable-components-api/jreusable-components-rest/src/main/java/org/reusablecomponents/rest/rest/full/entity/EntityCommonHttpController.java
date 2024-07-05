package org.reusablecomponents.rest.rest.full.entity;

import org.reusablecomponents.base.core.domain.AbstractEntity;

public class EntityCommonHttpController <Entity extends AbstractEntity<Id>, Id, HttpResponse> {
//      implements InterfaceEntityCommandHttpController<Entity, Id, HttpResponse> {
//
//    protected final InterfaceEntityCommandHttpController<Entity, Id, HttpResponse> interfaceEntityCommandHttpController;
//
//    protected EntityCommonHttpController(final InterfaceEntityCommandHttpController<Entity, Id, HttpResponse> interfaceEntityCommandHttpController) {
//	super();
//	this.interfaceEntityCommandHttpController = interfaceEntityCommandHttpController;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public HttpResponse post(@Valid Entity entity, HttpServletRequest request, HttpServletResponse response) {
//	return interfaceEntityCommandHttpController.post(entity, request, response);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public HttpResponse delete(Id id, HttpServletRequest request, HttpServletResponse response) {
//	return interfaceEntityCommandHttpController.delete(id, request, response);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public HttpResponse put(Id id, Entity entity, HttpServletRequest request, HttpServletResponse response) {
//	return interfaceEntityCommandHttpController.put(id, entity, request, response);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public HttpResponse patch(Id id, List<JsonPatch> jsonPatchs, HttpServletRequest request, HttpServletResponse response) {
//	return interfaceEntityCommandHttpController.patch(id, jsonPatchs, request, response);
//    }
}
