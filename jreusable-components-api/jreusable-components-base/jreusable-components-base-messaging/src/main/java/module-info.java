open module org.reusablecomponent.base.messaging {
    
    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires org.slf4j;
    
    // import non-modules
    requires org.apache.commons.lang3;
    
    //---------------------------------------------------
    // export
    exports org.reusablecomponents.base.messaging;
    exports org.reusablecomponents.base.messaging.logger;
    exports org.reusablecomponents.base.messaging.flow;
    exports org.reusablecomponents.base.messaging.event;
    exports org.reusablecomponents.base.messaging.operation;
}
