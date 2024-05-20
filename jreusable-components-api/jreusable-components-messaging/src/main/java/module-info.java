open module org.reusablecomponent.messaging {
    
    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires org.slf4j;
    
    // import non-modules
    requires org.apache.commons.lang3;
    
    //---------------------------------------------------
    // export
    exports org.reusablecomponent.messaging;
    exports org.reusablecomponent.messaging.logger;
    exports org.reusablecomponent.messaging.flow;
}
