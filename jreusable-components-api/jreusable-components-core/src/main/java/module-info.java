open module org.reusablecomponent.core {
    
    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires org.slf4j;
    
    // import non-modules
    requires com.google.common;
    requires org.apache.commons.lang3;
    
    // export
    // -- domain classes
    exports org.reusablecomponent.core.domain;
    
    // -- command facades
    exports org.reusablecomponent.core.application.command.entity;

    // -- queries facades
    exports org.reusablecomponent.core.application.query.entity.paged;
    exports org.reusablecomponent.core.application.query.entity.nonpaged;
    
    // -- full facades
    exports org.reusablecomponent.core.application.full.entity.paged;
    exports org.reusablecomponent.core.application.full.entity.nonpaged;
}
