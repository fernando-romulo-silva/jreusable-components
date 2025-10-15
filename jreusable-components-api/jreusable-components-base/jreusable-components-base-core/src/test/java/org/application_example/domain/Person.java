package org.application_example.domain;

import static org.reusablecomponents.base.core.infra.util.AbstractValidatorTest.VALIDATOR;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.domain.AbstractEntityBuilder;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

public class Person extends AbstractEntity<Long> {

    // ------------------- fields

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

    // ------------------- constructors

    // just for frameworks, like JPA
    Person() {
        super();
    }

    private Person(@NotNull final Builder builder) {

        super();

        this.id = builder.id;
        this.name = builder.name;
        this.createdReason = builder.createdReason;
        this.country = builder.country;
        this.hobbies = builder.hobbies;
        this.birthDate = builder.birthDate;
        this.score = builder.score;
        this.gender = builder.gender;
        this.hobbies.addAll(builder.hobbies);
    }

    // -------------------- getters
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

    // ------------------- Builder

    public static class Builder extends AbstractEntityBuilder<Long, Person, Builder> {

        public Long id;

        public String name;

        public Integer score;

        public String country;

        public List<Hobby> hobbies = new ArrayList<>();

        public LocalDate birthDate;

        public Gender gender;

        @NotNull
        @Valid
        @Override
        public Person build() {
            return validate(new Person(this));
        }

        @Override
        protected Validator getValidator() {
            return VALIDATOR;
        }
    }
}
