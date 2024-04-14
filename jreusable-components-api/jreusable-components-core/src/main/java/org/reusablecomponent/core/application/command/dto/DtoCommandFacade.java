package org.reusablecomponent.core.application.command.dto;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.core.infra.exception.ElementWithIdNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class DtoCommandFacade <DTO, Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, VoidResult> // Operations on Dtos
	//
	extends AbstractEntiyBaseFacade<Entity, Id>  // Base facade
	//
	implements InterfaceDtoCommandFacade<DTO, Entity, Id, OneResult, MultipleResult> { 

    protected final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> entityCommandFacade;
    
    protected final Function<Id, Optional<Entity>> findByIdFunction;
    
    public DtoCommandFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> entityCommandFacade, 
		    @NotNull final Function<Id, Optional<Entity>> findByIdFunction) {
	super();
	this.entityCommandFacade = entityCommandFacade;
	this.findByIdFunction = findByIdFunction;
    }

    /**
     * {@inheritDoc}
     */
    @Valid
    @NotNull
    @Override
    public OneResult save(
		    @NotNull final DTO dto, 
		    @NotNull final Function<@NotNull DTO, @NotNull Entity> convertToEntity, 
		    @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto) {

	final var entity = convertToEntity.apply(dto);

	final var newEntity = entityCommandFacade.save(entity);
	
	//final var newDto = convertToDto.apply(newEntity);

	// TODO how about Mono<Entity>?
	return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult saveAll(
		    @NotNull final Iterable<@NotNull DTO> dtos, 
		    @NotNull final Function<@NotNull DTO, @NotNull Entity> convertToEntity, 
		    @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto) {
	
	final var entities = StreamSupport.stream(dtos.spliterator(), false)
				.map(convertToEntity)
				.toList();

	final var newEntities = entityCommandFacade.saveAll(entities);

//	final var newDtos = StreamSupport.stream(newEntities.spliterator(), false)
//				.map(convertToDto)
//				.toList();
	
	// TODO how about Mono<Entity>?		
	return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Valid
    @NotNull
    @Override
    public OneResult update(
		    @NotNull final Id id,
		    @NotNull final DTO dto, 
		    @NotNull final BiFunction<@NotNull DTO, @NotNull Entity, @NotNull Entity> updateEntityData, 
		    @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto) {

	final var entity = findByIdFunction.apply(id)
			.orElseThrow(() -> new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), id));

	final var entityToUpdate = updateEntityData.apply(dto, entity);

	// TODO how about Mono<Entity>?
	@SuppressWarnings("unchecked")
	final var entityUpdated = (Entity) entityCommandFacade.update(entityToUpdate);

	final var result = convertToDto.apply(entityUpdated);

	// TODO how about Mono<Entity>?		
	return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public MultipleResult updateAll(
		    @NotNull final Iterable<@NotNull Id> ids, 
		    @NotNull final Iterable<@NotNull DTO> dtos, 
		    @NotNull final BiFunction<@NotNull DTO, @NotNull Entity, @NotNull Entity> updateEntityData, 
		    @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto) {
	
	// TODO check ids and dtos has the same size 
	
	final var idEntities = StreamSupport.stream(ids.spliterator(), false)
		.collect(Collectors.toMap(id -> id, findByIdFunction::apply));
	
	final var data = new ArrayList<Entry<DTO, Entity>>(idEntities.size());
	
	final var dtosIterator = dtos.iterator();
	
	for (final var idEntity : idEntities.entrySet()) {

	    final var dto = dtosIterator.next();
	    final var id = idEntity.getKey();
	    final var optionalEntity = idEntity.getValue();
	    
	    if (optionalEntity.isEmpty()) {
		throw new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), id);
	    }
	    
	    data.add(new SimpleEntry<>(dto, optionalEntity.get()));
	}
	
	final var entitiesToUpdate = data.stream()
		.map(d -> updateEntityData.apply(d.getKey(), d.getValue()))
		.toList();

	final var entitiesUpdated = entityCommandFacade.updateAll(entitiesToUpdate);
	
	return entitiesUpdated;
    }
}
