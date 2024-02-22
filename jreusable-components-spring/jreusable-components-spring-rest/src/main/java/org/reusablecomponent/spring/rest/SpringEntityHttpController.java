package org.reusablecomponent.spring.rest;

import java.util.List;

import org.reusablecomponent.domain.AbstractEntity;
import org.reusablecomponent.infra.jsonpath.JsonPatch;
import org.reusablecomponent.rest.EntityHttpController;
import org.reusablecomponent.spring.rest.command.InterfaceSpringEntityCommandHttpController;
import org.reusablecomponent.spring.rest.query.InterfaceSpringEntityQueryHttpController;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

public class SpringEntityHttpController<Entity extends AbstractEntity<Id>, Id>
//	extends EntityHttpController <Entity, Id, ResponseEntity<?>> 
	implements InterfaceSpringEntityQueryHttpController<Entity, Id>, 
	           InterfaceSpringEntityCommandHttpController<Entity, Id> {

    protected final InterfaceSpringEntityQueryHttpController<Entity, Id> interfaceSpringEntityQueryHttpController;
    
    protected final InterfaceSpringEntityCommandHttpController<Entity, Id> interfaceSpringEntityCommandHttpController;
    
    protected SpringEntityHttpController(
		    @NotNull InterfaceSpringEntityCommandHttpController<Entity, Id> interfaceEntityCommandHttpController,
		    @NotNull InterfaceSpringEntityQueryHttpController<Entity, Id> interfaceSpringEntityQueryHttpController) {
//	super(interfaceEntityCommandHttpController, interfaceSpringEntityQueryHttpController);
	this.interfaceSpringEntityQueryHttpController = interfaceSpringEntityQueryHttpController; 
	this.interfaceSpringEntityCommandHttpController = interfaceEntityCommandHttpController;
    }

    @Override
    public ResponseEntity<Entity> post(Entity entity, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ResponseEntity<Entity> put(Id id, Entity entity, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ResponseEntity<?> patch(Id id, List<JsonPatch> jsonPatchs, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ResponseEntity<?> delete(Id id, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ResponseEntity<?> get(Id id, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ResponseEntity<?> getAll(HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	return null;
    }
    
//    @Override
//    public ResponseEntity<Entity> get(Id id, HttpServletRequest request, HttpServletResponse response) {
//	return interfaceSpringEntityQueryHttpController.get(id, request, response);
//    }
//    
//    @Override
//    public ResponseEntity<List<Entity>> getAll(HttpServletRequest request, HttpServletResponse response) {
//	return interfaceSpringEntityQueryHttpController.getAll(request, response);
//    }
}
