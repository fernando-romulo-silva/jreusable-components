package org.reusablecomponent.core.application.query.entity;

import java.util.Objects;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

public class AbstractEntityQueryFacade<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, ExistsResult, CountResult> extends AbstractEntiyBaseFacade<Entity, Id> {

    protected String convertQueryIdInToPublishData(final QueryIdIn queryIdIn) {
	return Objects.toString(queryIdIn);
    }   
    
    protected String convertMultipleResultToPublishData(final MultipleResult multipleResult) {
	return Objects.toString(multipleResult);
    }
    
    protected String convertOneResultToPublishData(final OneResult oneResult) {
	return Objects.toString(oneResult);
    }
    
    protected String convertExistsResultToPublishData(final ExistsResult resultFinal) {
	return Objects.toString(resultFinal);
    }    
    
    protected String convertCountResultToPublishData(final CountResult countResult) {
	return Objects.toString(countResult);
    }  
}
