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


public class DtoCommandFacade // Operations on Dtos
	//
	extends AbstractEntiyBaseFacade  // Base facade
	//
//	implements InterfaceDtoCommandFacade 
	
	{ 

    protected final InterfaceEntityCommandFacade entityCommandFacade;
    // <Entity, Id, OneResult, MultipleResult, VoidResult, WrapEntity, WrapEntities, WrapIds>
    
    // protected final Function<Id, Optional<Entity>> findByIdFunction;
    
    public DtoCommandFacade(
		    @NotNull final InterfaceEntityCommandFacade entityCommandFacade) {
	super();
	this.entityCommandFacade = entityCommandFacade;
//	this.findByIdFunction = findByIdFunction;
    }
    
    //     @NotNull final Function<@NotNull DTO, @NotNull Entity> convertToEntity, 
    // @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto

    /**
     * {@inheritDoc}
     */
//    @Valid
//    @NotNull
//    @Override
//    public OneResult save(@NotNull final DTO dto) {

//	final var entity = convertToEntity.apply(dto);

//	final var newEntity = entityCommandFacade.save(entity);
	
	//final var newDto = convertToDto.apply(newEntity);

	// TODO how about Mono<Entity>?
//	return null;
//    }
    
    

//    @NotNull final Iterable<@NotNull DTO> dtos, 
//    @NotNull final Function<@NotNull DTO, @NotNull Entity> convertToEntity, 
//    @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto    
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public MultipleResult saveAll() {
	
//	final var entities = StreamSupport.stream(dtos.spliterator(), false)
//				.map(convertToEntity)
//				.toList();

//	final var newEntities = entityCommandFacade.saveAll((MultipleResult)entities);

//	final var newDtos = StreamSupport.stream(newEntities.spliterator(), false)
//				.map(convertToDto)
//				.toList();
	
	// TODO how about Mono<Entity>?		
//	return null;
//    }
    
    /**
     * {@inheritDoc}
     */
//    @Valid
//    @NotNull
//    @Override
//    public OneResult update(
//		    @NotNull final Id id,
//		    @NotNull final DTO dto, 
//		    @NotNull final BiFunction<@NotNull DTO, @NotNull Entity, @NotNull Entity> updateEntityData, 
//		    @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto) {
//
//	final var entity = findByIdFunction.apply(id)
//			.orElseThrow(() -> new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), id));
//
//	final var entityToUpdate = updateEntityData.apply(dto, entity);
//
//	// TODO how about Mono<Entity>?
////	final var entityUpdated = (Entity) entityCommandFacade.update(entityToUpdate);
//
////	final var result = convertToDto.apply(entityUpdated);
//
//	// TODO how about Mono<Entity>?		
//	return null;
//    }
    
    /**
     * {@inheritDoc}
     */
//    @NotNull
//    @Override
//    public MultipleResult updateAll(
//		    @NotNull final Iterable<@NotNull Id> ids, 
//		    @NotNull final Iterable<@NotNull DTO> dtos, 
//		    @NotNull final BiFunction<@NotNull DTO, @NotNull Entity, @NotNull Entity> updateEntityData, 
//		    @NotNull final Function<@NotNull Entity, @NotNull DTO> convertToDto) {
//	
//	// TODO check ids and dtos has the same size 
//	
//	final var idEntities = StreamSupport.stream(ids.spliterator(), false)
//		.collect(Collectors.toMap(id -> id, findByIdFunction::apply));
//	
//	final var data = new ArrayList<Entry<DTO, Entity>>(idEntities.size());
//	
//	final var dtosIterator = dtos.iterator();
//	
//	for (final var idEntity : idEntities.entrySet()) {
//
//	    final var dto = dtosIterator.next();
//	    final var id = idEntity.getKey();
//	    final var optionalEntity = idEntity.getValue();
//	    
//	    if (optionalEntity.isEmpty()) {
//		throw new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), id);
//	    }
//	    
//	    data.add(new SimpleEntry<>(dto, optionalEntity.get()));
//	}
//	
//	final var entitiesToUpdate = data.stream()
//		.map(d -> updateEntityData.apply(d.getKey(), d.getValue()))
//		.toList();
//
////	final var entitiesUpdated = entityCommandFacade.updateAll((MultipleResult) entitiesToUpdate);
//	
//	return null;
//    }
}
