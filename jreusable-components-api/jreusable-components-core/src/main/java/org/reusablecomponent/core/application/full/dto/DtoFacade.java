package org.reusablecomponent.core.application.full.dto;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.application.command.dto.DtoCommandFacade;
import org.reusablecomponent.core.application.command.dto.InterfaceDtoCommandFacade;
import org.reusablecomponent.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponent.core.application.query.dto.DtoQueryFacade;
import org.reusablecomponent.core.application.query.dto.InterfaceDtoQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <DTO>
 * @param <Entity>
 * @param <Id>
 * @param <OneResultCommand>
 * @param <OneResultQuery>
 * @param <MultipleResult>
 * @param <ExistsResult>
 * @param <VoidResult>
 */
public class DtoFacade <DTO, Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery, MultipleResult, ExistsResult, VoidResult> //
	//
	extends AbstractEntiyBaseFacade<Entity, Id>  // base
	// interfaces
 	implements InterfaceDtoCommandFacade<DTO, Entity, Id, OneResultCommand, MultipleResult>, // 
 		  InterfaceDtoQueryFacade<Id, OneResultQuery, MultipleResult> { 
    
    protected final InterfaceDtoCommandFacade<DTO, Entity, Id, OneResultCommand, MultipleResult> dtoCommandFacade;
    
    protected final InterfaceDtoQueryFacade<Id, OneResultQuery, MultipleResult> dtoQueryFacade;
    
    /**
     * @param dtoCommandFacade
     * @param dtoQueryFacade
     */
    protected DtoFacade(
		    @NotNull final InterfaceDtoCommandFacade<DTO, Entity, Id, OneResultCommand, MultipleResult> dtoCommandFacade, 
		    @NotNull final InterfaceDtoQueryFacade<Id, OneResultQuery, MultipleResult> dtoQueryFacade) {
	super();
	this.dtoCommandFacade = dtoCommandFacade;
	this.dtoQueryFacade = dtoQueryFacade;
    }
    
    /**
     * @param saveFunction
     * @param saveAllFunction
     * @param deleteFunction
     * @param deleteAllFunction
     * @param deleteByIdFunction
     * @param deleteAllByIdFunction
     * @param existsByIdFunction
     * @param existsEntityFunction
     * @param findByIdFunction
     * @param findEntityByIdFunction
     * @param findAllFunction
     */
    protected DtoFacade(
		    final Function<Entity, OneResultCommand> saveFunction,
		    final Function<Iterable<Entity>, MultipleResult> saveAllFunction,
		    //
		    final Function<Entity, VoidResult> deleteFunction,
		    final Function<Iterable<Entity>, VoidResult> deleteAllFunction,
		    final Function<Id, VoidResult> deleteByIdFunction,
		    final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction,
		    //
		    final Function<Id, ExistsResult> existsByIdFunction,
		    final Predicate<ExistsResult> existsEntityFunction,
		    //
		    final Function<Id, OneResultQuery> findByIdFunction,
		    final Function<Id, Optional<Entity>> findEntityByIdFunction,
		    final Supplier<MultipleResult> findAllFunction) {
	super();
	
	this.dtoQueryFacade = new DtoQueryFacade<>(findByIdFunction, findAllFunction);
	
	final var entityCommandFacade = new EntityCommandFacade<>(		
			saveFunction, 
			saveAllFunction, 
			deleteFunction, 
			deleteAllFunction, 
			deleteByIdFunction, 
			deleteAllByIdFunction, 
			existsByIdFunction,
			existsEntityFunction);
	
	this.dtoCommandFacade = new DtoCommandFacade<>(entityCommandFacade, findEntityByIdFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findAll(final Map<String, String[]> directives) {
	return dtoQueryFacade.findAll(directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResultQuery findById(final Id id) {
	return dtoQueryFacade.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResultCommand save(final DTO dto, final Function<DTO, Entity> convertToEntity, final Function<Entity, DTO> convertToDto) {
	return dtoCommandFacade.save(dto, convertToEntity, convertToDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult saveAll(Iterable<DTO> dtos, Function<DTO, Entity> convertToEntity, Function<Entity, DTO> convertToDto) {
	return dtoCommandFacade.saveAll(dtos, convertToEntity, convertToDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResultCommand update(final Id id, final DTO dto, final BiFunction<DTO, Entity, Entity> updateEntityData, final Function<Entity, DTO> convertToDto) {
	return dtoCommandFacade.update(id, dto, updateEntityData, convertToDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult updateAll(final Iterable<Id> ids, final Iterable<DTO> dtos, final BiFunction<DTO, Entity, Entity> updateEntityData, final Function<Entity, DTO> convertToDto) {
	return dtoCommandFacade.updateAll(ids, dtos, updateEntityData, convertToDto);
    }
}
