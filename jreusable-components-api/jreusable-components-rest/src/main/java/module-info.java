module org.reusablecomponent.rest {
    
    // modules
    requires org.reusablecomponent.core;
    
    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires jakarta.servlet;
    requires org.slf4j;

    // non-modules
    requires org.apache.commons.lang3;
    requires io.swagger.v3.oas.models;
    requires io.swagger.v3.oas.annotations;
    requires org.apache.commons.collections4;
}