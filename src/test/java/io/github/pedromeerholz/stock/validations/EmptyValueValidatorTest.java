package io.github.pedromeerholz.stock.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmptyValueValidatorTest {
    private EmptyValueValidator emptyValueValidator = new EmptyValueValidator();

    @Test
    void emptyValueValidation() {
        String value = "Pedro";
        boolean result = this.emptyValueValidator.emptyValueValidation(value);
        Assertions.assertEquals(true, result);
    }
}