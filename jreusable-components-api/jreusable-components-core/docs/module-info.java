open module org.reusablecomponents.core {
    
    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;

    
    // import non-modules
    requires com.google.common;
    requires org.apache.commons.lang3;
    requires com.google.gson;
    requires gson.javatime.serialisers;
    
    // -- messaging
    requires org.reusablecomponents.messaging;
//    requires org.reusablecomponents.messaging.event;
//    requires org.reusablecomponents.messaging.logger;
//    requires org.reusablecomponents.messaging.flow;
//    requires org.reusablecomponents.messaging.util;
    
    //---------------------------------------------------
    // export
    // -- domain classes
    exports org.reusablecomponents.core.domain;
    
    // -- command facades
    exports org.reusablecomponents.core.application.command.entity;

    // -- queries facades
    exports org.reusablecomponents.core.application.query.entity.paged;
    exports org.reusablecomponents.core.application.query.entity.nonpaged;
    
    // -- full facades
    exports org.reusablecomponents.core.application.mix.entity.paged;
    exports org.reusablecomponents.core.application.mix.entity.nonpaged;
    
    // -- exception
    exports org.reusablecomponents.core.infra.exception;
    
    // -- i18n
    exports org.reusablecomponents.core.infra.i18n;
    
    // -- security
    exports org.reusablecomponents.core.infra.security;
    
    // -- messaging
}
