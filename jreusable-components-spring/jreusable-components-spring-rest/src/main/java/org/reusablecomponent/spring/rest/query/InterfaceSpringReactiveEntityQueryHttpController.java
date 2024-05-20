package org.reusablecomponent.spring.rest.query;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import org.reactivestreams.Publisher;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.entity.nonpaged.InterfaceEntityQueryHttpController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringReactiveEntityQueryHttpController<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceEntityQueryHttpController<Publisher<Id>, ResponseEntity<Mono<Void>>, ResponseEntity<Mono<Entity>>, ResponseEntity<Flux<Entity>>> {

    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @GetMapping(value = "/{id}", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    ResponseEntity<Mono<Entity>> get(@PathVariable final Publisher<Id> id, final HttpServletRequest request, final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @GetMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    ResponseEntity<Flux<Entity>> getAll(final HttpServletRequest request, final HttpServletResponse response);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @RequestMapping(method = HEAD, value = "/{id}", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    ResponseEntity<Mono<Void>> head(@PathVariable final Publisher<Id> id, final HttpServletRequest request, final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @Override
    @ResponseStatus(OK)
    @RequestMapping(method = HEAD, produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    ResponseEntity<Mono<Void>> headAll(final HttpServletRequest request, final HttpServletResponse response);

}