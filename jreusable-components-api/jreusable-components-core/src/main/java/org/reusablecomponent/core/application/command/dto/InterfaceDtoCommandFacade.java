package org.reusablecomponent.core.application.command.dto;

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
    
    // Function<DTO, Entity> convertToEntity, final Function<Entity, DTO> convertToDto
    OneResult save(final DTO dto);

    /**
     * @param dtos
     * @param convertToEntity
     * @param convertToDto
     * @return
     */
    MultipleResult saveAll(final Iterable<DTO> dtos);

    /**
     * @param id
     * @param dto
     * @param updateEntityData
     * @param convertToDto
     * @return
     */
    
    // final BiFunction<DTO, Entity, Entity> updateEntityData, final Function<Entity, DTO> convertToDto
    OneResult update(final Id id, final DTO dto);

    /**
     * @param ids
     * @param dtos
     * @param convertToEntity
     * @param convertToDto
     * @return
     */
    // final BiFunction<DTO, Entity, Entity> updateEntityData, final Function<Entity, DTO> convertToDto
    MultipleResult updateAll(final Iterable<Id> ids, final Iterable<DTO> dtos);      

}
