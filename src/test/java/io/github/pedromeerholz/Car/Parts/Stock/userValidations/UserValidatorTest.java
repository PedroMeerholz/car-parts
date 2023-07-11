package io.github.pedromeerholz.Car.Parts.Stock.userValidations;

import io.github.pedromeerholz.Car.Parts.Stock.validations.userValidations.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserValidatorTest {
    private UserValidator userValidator = new UserValidator();

    @Test
    void validateEmail() {
        String email = "pedro@gmail.br";
        boolean result = this.userValidator.validateEmail(email);
        Assertions.assertEquals(true, result);
    }

    @Test
    void validatePassword() {
        String password = "12345678";
        boolean result = this.userValidator.validatePasswordPattern(password);
        Assertions.assertEquals(true, result);
    }

    @Test
    void validateUserDataToCreate() {
        String name = "Pedro";
        String email = "pedro@gmail.com";
        String password = "123456789";
        boolean result = this.userValidator.validateUserDataToCreate(name, email, password);
        Assertions.assertEquals(true, result);
    }

    @Test
    void validadeUserDataToUpdate() {
        String name = "Pedro";
        String email = "pedro@gmail.com";
        boolean result = this.userValidator.validateUserDataToUpdate(name, email);
        Assertions.assertEquals(true, result);
    }

    @Test
    void validatePasswordToUpdate() {
        String password = "12345678";
        boolean result = this.userValidator.validatePasswordToUpdate(password);
        Assertions.assertEquals(true, result);
    }
}