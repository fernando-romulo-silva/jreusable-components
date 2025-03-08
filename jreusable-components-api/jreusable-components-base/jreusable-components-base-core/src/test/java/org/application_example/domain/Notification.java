package org.application_example.domain;

import java.time.LocalDateTime;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.domain.AbstractEntityBuilder;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

public class Notification extends AbstractEntity<Long> {

    private final String name;

    private final String description;

    private final Integer sequence;

    private final LocalDateTime dateTime;

    // ------------------- constructors

    private Notification(final Builder builder) {
        super();

        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.sequence = builder.sequence;
        this.dateTime = builder.dateTime;
    }

    // -----------------------------------

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // -----------------------------------

    public static class Builder extends AbstractEntityBuilder<Long, Notification, Builder> {

        public Long id;

        public String name;

        public String description;

        public Integer sequence;

        public LocalDateTime dateTime;

        @NotNull
        @Valid
        @Override
        public Notification build() {
            return validate(new Notification(this));
        }

        @Override
        public Validator getValidator() {
            return null;
        }
    }
}
