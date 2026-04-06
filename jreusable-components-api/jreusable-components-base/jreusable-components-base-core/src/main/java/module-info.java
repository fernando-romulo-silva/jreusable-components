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

    // -- messaging
    requires transitive org.reusablecomponent.base.security;
    requires transitive org.reusablecomponents.base.translation;

    // ---------------------------------------------------
    // export
    // -- domain classes
    exports org.reusablecomponents.base.core.domain;

    // -- base facade
    exports org.reusablecomponents.base.core.application.base;

    // -- empty facade
    exports org.reusablecomponents.base.core.application.empty;

    // -- command facades
    exports org.reusablecomponents.base.core.application.command.entity;
    exports org.reusablecomponents.base.core.application.command.entity.function.save;

    // -- queries facades
    exports org.reusablecomponents.base.core.application.query.entity.simple;
    exports org.reusablecomponents.base.core.application.query.entity.simple.function.find_all;
    exports org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id;
    exports org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id;
    exports org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all;
    exports org.reusablecomponents.base.core.application.query.entity.simple.function.count_all;

    exports org.reusablecomponents.base.core.application.query.entity.pagination;
    exports org.reusablecomponents.base.core.application.query.entity.paginationspecification;
    exports org.reusablecomponents.base.core.application.query.entity.specification;

    // -- full facades
    exports org.reusablecomponents.base.core.application.mix.entity;

    // -- exception
    exports org.reusablecomponents.base.core.infra.exception;
    exports org.reusablecomponents.base.core.infra.exception.common;

    // -- infra
    exports org.reusablecomponents.base.core.infra.constants;
    exports org.reusablecomponents.base.core.infra.util;
    exports org.reusablecomponents.base.core.infra.util.function;
    exports org.reusablecomponents.base.core.infra.util.function.operation;

    exports org.reusablecomponents.base.core.infra.util.function.operation.custom;
}
