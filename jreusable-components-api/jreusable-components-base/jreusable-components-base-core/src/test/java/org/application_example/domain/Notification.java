package org.application_example.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import org.application_example.infra.Utils;

import org.reusablecomponents.base.core.domain.AbstractEntityWithBuilder;

import jakarta.validation.Validator;

public class Notification extends AbstractEntityWithBuilder<Long> {

    private final String name;

    private final String description;

    private final Integer sequence;

    private final LocalDateTime dateTime;

    private Notification(final Builder builder) {
        super();

        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.sequence = builder.sequence;
        this.dateTime = builder.dateTime;
    }

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

    public static class Builder extends AbstractEntityBuilder<Long, Notification, Builder> {

        public Long id;

        public String name;

        public String description;

        public Integer sequence;

        public LocalDateTime dateTime;

        @Override
        protected Notification createInstance() {
            return new Notification(this);
        }

        @Override
        public Optional<Validator> getValidator() {
            return Optional.of(Utils.VALIDATOR);
        }
    }
}
