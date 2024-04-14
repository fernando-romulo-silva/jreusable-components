package org.reusablecomponent.spring.rest.query;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.InterfaceEntityQueryHttpController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringEntityQueryHttpController<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceEntityQueryHttpController<Id, ResponseEntity<?>> {

    /**
     * {@inheritDoc}
     */
    @ResponseStatus(OK)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> get(@PathVariable final Id id, final HttpServletRequest request, final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> getAll(final HttpServletRequest request, final HttpServletResponse response);

}