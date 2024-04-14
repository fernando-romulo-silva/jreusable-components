package org.reusablecomponent.rest.infra.openapi.dynamic;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class DefaultOpenApiConfiguration {
    
    private String appName;

    private String appDesciption;

    public OpenAPI openAPI() {
	final var appVersion = getClass().getPackage().getImplementationVersion();
	final var appVendor = getClass().getPackage().getSpecificationVendor();

	return new OpenAPI() //
			.info(new Info() //
					.title(appName) //
					.version(appVersion + " " + appVendor) //
					.description(appDesciption) //
					.contact(new Contact().name("Fernando Romulo da Silva")).termsOfService("http://swagger.io/terms/") //
					.license(new License().name("Apache 2.0").url("http://springdoc.org")) //
			);
    }
    
    
    private String getClassRequestMapping(final Method method, final Class<? extends Annotation> classAnnotationClazz, final String propertyName) {

	final var requestMappingAnnotationClass = Stream.of(method.getDeclaringClass().getAnnotations()) //
			.filter(p -> Objects.equals(p.annotationType(), classAnnotationClazz)) //
			.findFirst() //
			.orElse(null);

	if (Objects.nonNull(requestMappingAnnotationClass)) {

	    final var values = getValueAnnotation(requestMappingAnnotationClass, propertyName);

	    if (!ArrayUtils.isEmpty(values)) {

		return Stream.of(StringUtils.split(values[0], "/")) //
				.filter(s -> !StringUtils.containsAny(s, "{}")) //
				.collect(Collectors.joining("'s"));
	    }
	}

	return StringUtils.EMPTY;
    }

    private String getMethodRequestMapping(final Method method, final Class<? extends Annotation> methodAnnotationClazz, final String propertyName) {

	final var requestMappingAnnotationClass = Stream.of(method.getDeclaringClass().getAnnotations()) //
			.filter(p -> Objects.equals(p.annotationType(), methodAnnotationClazz)) //
			.findFirst() //
			.orElse(null);

	if (Objects.nonNull(requestMappingAnnotationClass)) {

	    final var values = getValueAnnotation(requestMappingAnnotationClass, propertyName);

	    if (!ArrayUtils.isEmpty(values)) {

		return Stream.of(StringUtils.split(values[0], "/")) //
				.filter(s -> !StringUtils.containsAny(s, "{}")) //
				.reduce((first, second) -> second) //
				.map(m -> "'s " + m) //
				.orElse(StringUtils.EMPTY);
	    }
	}

	return StringUtils.EMPTY;
    }
    
    private String[] getValueAnnotation(final Annotation annotation, final String attributeName) {

	Object value = ArrayUtils.EMPTY_STRING_ARRAY;

	try {
	    final var method = annotation.annotationType().getDeclaredMethod(attributeName);
	    value = method.invoke(annotation, (Object[]) null);
	} catch (final NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
	    
	}

	return (String[]) value;
    }
    
    public Operation updateOperation(
		    final Operation operation, 
		    final Method controllerMethod, 
		    final Class<? extends Annotation> httpClazz, 
		    final String httpMethod, 
		    final String propertyName) {
	
	if (StringUtils.isNotBlank(operation.getDescription()) || StringUtils.isNotBlank(operation.getSummary())) {
	    return operation;
	}

	final var classEntity = getClassRequestMapping(controllerMethod, httpClazz, propertyName);

	final var listAnnotationTypes = Stream.of(controllerMethod.getAnnotations()) //
			.map(a -> a.annotationType()) //
			.toList();

	final var methodEntity = getMethodRequestMapping(controllerMethod, httpClazz, propertyName);
	
	if (listAnnotationTypes.contains(httpClazz) && equalsIgnoreCase(httpMethod, "get")) {

	    operation.description("Get " + classEntity + methodEntity);
	    operation.operationId("getItem");
	    operation.summary("Get items bla bla");
	    operation.addExtension("x-operationWeight", "200");

	    return operation;
	}
	
	if (listAnnotationTypes.contains(httpClazz) && equalsIgnoreCase(httpMethod, "head")) {

	    operation.description("Head " + classEntity + methodEntity);
	    operation.operationId("getItem");
	    operation.summary("Head items bla bla");
	    operation.addExtension("x-operationWeight", "200");

	    return operation;
	}	

	if (listAnnotationTypes.contains(httpClazz) && equalsIgnoreCase(httpMethod, "post")) {

	    operation.description("Create " + classEntity + methodEntity);
	    operation.operationId("createItem");
	    operation.summary("Create items bla bla");
	    operation.addExtension("x-operationWeight", "300");

	    return operation;
	}

	if (listAnnotationTypes.contains(httpClazz) && equalsIgnoreCase(httpMethod, "put")) {

	    operation.description("Update " + classEntity + methodEntity);
	    operation.operationId("updateItem");
	    operation.summary("Update items bla bla");
	    operation.addExtension("x-operationWeight", "400");

	    return operation;
	}
	
	if (listAnnotationTypes.contains(httpClazz) && equalsIgnoreCase(httpMethod, "patch")) {

	    operation.description("Patch " + classEntity + methodEntity);
	    operation.operationId("PatchItem");
	    operation.summary("Patch items bla bla");
	    operation.addExtension("x-operationWeight", "400");

	    return operation;
	}	

	if (listAnnotationTypes.contains(httpClazz) && equalsIgnoreCase(httpMethod, "delete")) {	    

	    operation.description("Delete " + classEntity + methodEntity);
	    operation.operationId("deleteItem");
	    operation.summary("Delete items bla bla");
	    operation.addExtension("x-operationWeight", "500");

	    return operation;
	}

	return operation;
    }
}
