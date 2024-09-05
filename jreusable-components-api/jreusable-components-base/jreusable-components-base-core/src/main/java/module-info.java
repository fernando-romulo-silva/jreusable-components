open module org.reusablecomponent.base.core {

    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;

    // import non-modules
    requires com.google.common;
    requires org.apache.commons.lang3;
    // requires com.google.gson;
    // requires gson.javatime.serialisers;

    // -- messaging
    requires transitive org.reusablecomponent.base.messaging;
    requires transitive org.reusablecomponent.base.security;

    requires transitive org.reusablecomponents.base.translation;

    // ---------------------------------------------------
    // export
    // -- domain classes
    exports org.reusablecomponents.base.core.domain;

    // -- command facades
    exports org.reusablecomponents.base.core.application.command.entity;

    // -- queries facades
    exports org.reusablecomponents.base.core.application.query.entity.paged;
    exports org.reusablecomponents.base.core.application.query.entity.nonpaged;

    // -- full facades
    exports org.reusablecomponents.base.core.application.mix.entity.paged;
    exports org.reusablecomponents.base.core.application.mix.entity.nonpaged;

    // -- exception
    exports org.reusablecomponents.base.core.infra.exception;
    exports org.reusablecomponents.base.core.infra.exception.common;

}
