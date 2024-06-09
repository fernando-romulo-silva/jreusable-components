package org.reusablecomponent.spring.rest.query.entity.nonpaged;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.entity.nonpaged.InterfaceEntityQueryHttpController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringEntityQueryHttpController<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceEntityQueryHttpController<Id, ResponseEntity<Void>, ResponseEntity<Entity>, ResponseEntity<Iterable<Entity>>> {

    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Entity> get(@PathVariable final Id id, final HttpServletRequest request, final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Iterable<Entity>> getAll(final HttpServletRequest request, final HttpServletResponse response);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> head(@PathVariable final Id id, final HttpServletRequest request, final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @RequestMapping(method = RequestMethod.HEAD, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> headAll(final HttpServletRequest request, final HttpServletResponse response);

}