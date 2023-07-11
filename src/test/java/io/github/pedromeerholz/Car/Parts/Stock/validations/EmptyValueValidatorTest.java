package io.github.pedromeerholz.Car.Parts.Stock.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyValueValidatorTest {
    private EmptyValueValidator emptyValueValidator = new EmptyValueValidator();

    @Test
    void emptyValueValidation() {
        String value = "Pedro";
        boolean result = this.emptyValueValidator.emptyValueValidation(value);
        Assertions.assertEquals(true, result);
    }
}