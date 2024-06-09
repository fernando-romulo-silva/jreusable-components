open module org.reusablecomponent.core {
    
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
    requires org.reusablecomponent.messaging;
//    requires org.reusablecomponent.messaging.event;
//    requires org.reusablecomponent.messaging.logger;
//    requires org.reusablecomponent.messaging.flow;
//    requires org.reusablecomponent.messaging.util;
    
    //---------------------------------------------------
    // export
    // -- domain classes
    exports org.reusablecomponent.core.domain;
    
    // -- command facades
    exports org.reusablecomponent.core.application.command.entity;

    // -- queries facades
    exports org.reusablecomponent.core.application.query.entity.paged;
    exports org.reusablecomponent.core.application.query.entity.nonpaged;
    
    // -- full facades
    exports org.reusablecomponent.core.application.mix.entity.paged;
    exports org.reusablecomponent.core.application.mix.entity.nonpaged;
    
    // -- exception
    exports org.reusablecomponent.core.infra.exception;
    
    // -- i18n
    exports org.reusablecomponent.core.infra.i18n;
    
    // -- security
    exports org.reusablecomponent.core.infra.security;
    
    // -- messaging
}
