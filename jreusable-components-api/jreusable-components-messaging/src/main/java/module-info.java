open module org.reusablecomponent.messaging {
    
    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires org.slf4j;
    
    // import non-modules
    requires org.apache.commons.lang3;
    
    //---------------------------------------------------
    // export
    exports org.reusablecomponents.messaging;
    exports org.reusablecomponents.messaging.logger;
    exports org.reusablecomponents.messaging.flow;
}
