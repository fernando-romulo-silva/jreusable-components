package org.application_example.infra;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class Utils {

    public static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    public static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();
}
