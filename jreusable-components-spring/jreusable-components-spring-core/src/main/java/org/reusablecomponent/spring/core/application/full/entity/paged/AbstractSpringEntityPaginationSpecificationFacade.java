package org.reusablecomponent.spring.core.application.full.entity.paged;

import java.util.Optional;

import org.reusablecomponent.core.application.full.entity.paged.EntityPaginationSpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringCommandFacade;
import org.reusablecomponent.spring.core.application.query.paged.InterfaceSpringEntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.spring.core.domain.InterfaceSpringPaginationRepository;
import org.reusablecomponent.spring.core.infra.i18n.SpringI18nService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
@Transactional(readOnly = true)
public abstract class AbstractSpringEntityPaginationSpecificationFacade <Entity extends AbstractEntity<Id>, Id, Specification> extends EntityPaginationSpecificationFacade<Entity, Id, 
        // ------------ command
        // save
        Entity, Entity, // save a entity
        Iterable<Entity>, Iterable<Entity>, // save entities
        // update
        Entity, Entity, // update a entity
        Iterable<Entity>, Iterable<Entity>, // update entities
        // delete entity
        Entity, Void, // delete a entity
        Iterable<Entity>, Void, // delete entities
        // delete by id
        Id, Void, // delete a entity by id
        Iterable<Id>, Void,
        // ------------ query
	// results
	Optional<Entity>, // one result type
	Page<Entity>, // multiple result type
	// Pagination
	Pageable, // pageable type
	Sort, // sort type
	Specification> // query spec
	//
	implements InterfaceSpringEntityPaginationSpecificationFacade<Entity, Id, Specification>{

    protected InterfaceSpringPaginationRepository<Entity, Id> repository;
    
    protected SpringI18nService i18n;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryPaginationFacade
     */
    protected AbstractSpringEntityPaginationSpecificationFacade(
		    final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade, 
		    final InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationFacade) {
	super(entityCommandFacade, entityQueryPaginationFacade);
    }
    
    /**
     * @param repository
     * @param i18n
     */
//    protected AbstractSpringEntityPaginationSpecificationFacade(final InterfaceSpringPaginationRepository<Entity, Id> repository, final SpringI18nService i18n) {
//	super(
//		repository::save, // saveFunction
//		repository::saveAll, // saveAllFunction
//		//
// 		entity -> { repository.delete(entity); return null;}, // deleteFunction
// 		entities -> { repository.deleteAll(entities); return null; }, // deleteAllFunction
// 		id -> { repository.deleteById(id); return null; }, // deleteByIdFunciton
// 		ids -> { repository.deleteAllById(ids); return null; }, // deleteAllByIdFunction
// 		//
// 		repository::existsById, // existsByIdFunction
// 		booleanResult -> booleanResult, // existsEntityFunction
//		//
//		(specification, pageable) -> Page.empty(), // findBySpecificationFunction
//		(specification, sort) -> Optional.empty() // findOneByFunctionWithOrder
//		
//	);
//	 // final BiFunction<Specification, Pageable, Page> findBySpecificationFunction, 
//	 // final BiFunction<Specification, Sort, Optional<Entity>> findOneByFunctionWithOrder
//	
//	this.repository = repository;
//	this.i18n = i18n;
//    }
}
