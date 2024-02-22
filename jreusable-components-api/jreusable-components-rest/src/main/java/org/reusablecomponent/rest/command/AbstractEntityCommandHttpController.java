package org.reusablecomponent.rest.command;

import static java.util.stream.Collectors.toMap;
import static org.reusablecomponent.infra.jsonpath.JsonPatchOperation.REPLACE;

import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.reusablecomponent.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.domain.AbstractEntity;
import org.reusablecomponent.infra.jsonpath.JsonPatch;
import org.reusablecomponent.rest.base.AbstractEntityBaseHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractEntityCommandHttpController <Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, VoidResult, HttpResponse>
        extends AbstractEntityBaseHttpController<Id, OneResult>
	implements InterfaceEntityCommandHttpController<Entity, Id, HttpResponse> {

    protected final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> interfaceEntityCommandFacade;
    
    
    /**
     * @param interfaceEntityCommandFacade
     */
    protected AbstractEntityCommandHttpController(@NotNull final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> interfaceEntityCommandFacade) {
	super();
	this.interfaceEntityCommandFacade = interfaceEntityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse post(final Entity entity, final HttpServletRequest request, final HttpServletResponse response) {

	final var result = getEntityResult(interfaceEntityCommandFacade.save(entity));
	
	final var url = request.getRequestURI();
	response.addHeader("Location", url + "/" + result.getId());
	response.setStatus(201);
	
	return createResponsePost(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse delete(final Id id, final HttpServletRequest request, final HttpServletResponse response) {
	
	response.setStatus(204);

	interfaceEntityCommandFacade.deleteBy(id);
	
	return createResponseDelete(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse put(final Id id, final Entity entity, final HttpServletRequest request, final HttpServletResponse response) {
	
	final var result = getEntityResult(interfaceEntityCommandFacade.update(id, entity));
	
	response.setStatus(204);
	
	return createResponsePut(result);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse patch(
		    final Id id, 
		    final List<JsonPatch> jsonPatchs, //
		    final HttpServletRequest request, //
		    final HttpServletResponse response) {
		    
	
	final var entity = getEntityResult(findById(id));
	
	final var entityPatched = applyPatchToJObject(jsonPatchs, entity);
	
	final var result = getEntityResult(interfaceEntityCommandFacade.update(entityPatched));
	
	response.setStatus(204);
	
	return createResponsePut(result);
    }
    
    /**
     * @param jsonPatchs
     * @param targetObject
     * @return
     */
    protected final Entity applyPatchToJObject(final List<JsonPatch> jsonPatchs, final Entity targetObject) {

	final var toReplace = jsonPatchs.stream() //
			.filter(jsonPatch -> jsonPatch.op().equals(REPLACE)) //
			.collect(toMap(JsonPatch::path, JsonPatch::value)); //

	toReplace.forEach((key, value) -> {
	    final var field = FieldUtils.getField(interfaceEntityCommandFacade.getClass(), key, true);
	    try {
		FieldUtils.writeField(field, targetObject, value, true);
	    } catch (final IllegalAccessException ex) {
		throw new IllegalArgumentException(ex);
	    }
	});

	return targetObject;
    }
    
    /**
     * @param objectResult
     * @return
     */
    protected abstract Entity getEntityResult(final OneResult objectResult);
    
    /**
     * @param <Response>
     * @param entity
     * @return
     */
    protected abstract HttpResponse createResponsePost(final Entity entity);

    /**
     * @param <Response>
     * @param entity
     * @return
     */
    protected abstract HttpResponse createResponseDelete(final Id id);

    /**
     * @param <Response>
     * @param entity
     * @return
     */
    protected abstract HttpResponse createResponsePut(final Entity entity);

    /**
     * @param <Response>
     * @param entity
     * @return
     */
    protected abstract HttpResponse createResponsePatch(final Entity entity);
}
