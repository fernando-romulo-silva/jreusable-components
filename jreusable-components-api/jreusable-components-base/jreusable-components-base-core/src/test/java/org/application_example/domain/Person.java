package org.application_example.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.application_example.infra.Utils;
import org.reusablecomponents.base.core.domain.AbstractEntityWithBuilder;

import jakarta.validation.Validator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

public class Person extends AbstractEntityWithBuilder<Long> {

    @NotEmpty
    private String name;

    @NotNull
    @Positive
    private Integer score;

    @NotEmpty
    private String country;

    @NotEmpty
    private List<Hobby> hobbies = new ArrayList<>();

    @Past
    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    Person() {
        super();
    }

    private Person(@NotNull final Builder builder) {
        super(builder);
        this.name = builder.name;
        this.createdReason = builder.createdReason;
        this.country = builder.country;
        this.hobbies = builder.hobbies;
        this.birthDate = builder.birthDate;
        this.score = builder.score;
        this.gender = builder.gender;
        this.hobbies.addAll(builder.hobbies);
    }

    @NotNull
    @Min(value = 1)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public String getCountry() {
        return country;
    }

    public List<Hobby> getHobbies() {
        return Collections.unmodifiableList(hobbies);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
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
                .append("score", score)
                .append("country", country)
                .append("hobbies", hobbies)
                .append("birthDate", birthDate)
                .append("gender", gender)
                .toString();
    }

    public static class Builder extends AbstractEntityWithBuilder.AbstractEntityBuilder<Long, Person, Builder> {

        public String name;

        public Integer score;

        public String country;

        public List<Hobby> hobbies = new ArrayList<>();

        public LocalDate birthDate;

        public Gender gender;

        @Override
        protected Person createInstance() {
            return new Person(this);
        }

        @Override
        public Optional<Validator> getValidator() {
            return Optional.of(Utils.VALIDATOR);
        }
    }
}
