package org.reusablecomponent.core.application.command.dto;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <DTO>
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 */
public interface InterfaceDtoCommandFacade<DTO, Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult> 
	extends InterfaceEntityBaseFacade<Entity, Id> {
    
    /**
     * @param dto
     * @param convertToEntity
     * @param convertToDto
     * @return
     */
    OneResult save(final DTO dto, Function<DTO, Entity> convertToEntity, final Function<Entity, DTO> convertToDto);

    /**
     * @param dtos
     * @param convertToEntity
     * @param convertToDto
     * @return
     */
    MultipleResult saveAll(final Iterable<DTO> dtos, final Function<DTO, Entity> convertToEntity, final Function<Entity, DTO> convertToDto);

    
    /**
     * @param id
     * @param dto
     * @param updateEntityData
     * @param convertToDto
     * @return
     */
    OneResult update(final Id id, DTO dto, final BiFunction<DTO, Entity, Entity> updateEntityData, final Function<Entity, DTO> convertToDto);

    
    /**
     * @param ids
     * @param dtos
     * @param convertToEntity
     * @param convertToDto
     * @return
     */
    MultipleResult updateAll(final Iterable<Id> ids, final Iterable<DTO> dtos, final BiFunction<DTO, Entity, Entity> updateEntityData, final Function<Entity, DTO> convertToDto);      

}
