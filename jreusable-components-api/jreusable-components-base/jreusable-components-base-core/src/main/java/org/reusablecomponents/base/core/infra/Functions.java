package org.reusablecomponents.base.core.infra;

import java.util.Objects;
import java.util.function.Supplier;

public class Functions {

    public static class ToStringSupplier implements Supplier<String> {

        private Object object;

        public ToStringSupplier(final Object object) {
            this.object = object;
        }

        @Override
        public String get() {
            return Objects.toString(object);
        }
    }

    public static final Supplier<String> f1 = new Supplier<String>() {

        @Override
        public String get() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'get'");
        }

    };
}
