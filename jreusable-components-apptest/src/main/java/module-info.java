open module org.apptest {

    // import modules
    requires jakarta.validation;
    requires jakarta.annotation;
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;

    requires org.reusablecomponent.base.core;

    // import non-modules
    requires transitive com.google.common;
    requires org.apache.commons.lang3;
    requires org.apache.commons.collections4;

    // ---------------------------------------------------
    // export
    // -- domain classes
    exports org.apptest.domain;

    // -- command facades
    exports org.apptest.application.command;

    // -- queries facades

    // -- full facades

    // -- exception

    // -- infra

}
