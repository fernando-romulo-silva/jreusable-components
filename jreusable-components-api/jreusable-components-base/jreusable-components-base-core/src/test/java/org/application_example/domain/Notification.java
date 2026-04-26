package org.application_example.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.application_example.infra.Utils;
import org.reusablecomponents.base.core.domain.AbstractEntityWithBuilder;

import jakarta.validation.Validator;

public class Notification extends AbstractEntityWithBuilder<Long> {

    private String name;

    private String description;

    private Integer sequence;

    private LocalDateTime dateTime;

    Notification() {
        super();
    }

    private Notification(final Builder builder) {
        super(builder);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("createdDate", createdDate)
                .append("createdReason", createdReason)
                .append("updatedDate", updatedDate)
                .append("updatedReason", updatedReason)
                .append("name", name)
                .append("description", description)
                .append("sequence", sequence)
                .append("dateTime", dateTime)
                .toString();
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
