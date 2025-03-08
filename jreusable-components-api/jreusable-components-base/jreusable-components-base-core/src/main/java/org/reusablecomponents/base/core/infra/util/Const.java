package org.reusablecomponents.base.core.infra.util;

public class Const {

    public static final String CAMEL_REGEX = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";

    private Const() {
        throw new UnsupportedOperationException("You can't instanciate this class");
    }
}
