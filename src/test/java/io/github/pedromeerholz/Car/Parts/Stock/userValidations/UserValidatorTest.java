package io.github.pedromeerholz.Car.Parts.Stock.userValidations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserValidatorTest {
    private UserValidator userValidator = new UserValidator();

    @Test
    void emptyValueValidation() {
        String value = "Pedro";
        boolean result = this.userValidator.emptyValueValidation(value);
        Assertions.assertEquals(true, result);
    }

    @Test
    void validateEmail() {
        String email = "pedro@gmail.br";
        boolean result = this.userValidator.validateEmail(email);
        Assertions.assertEquals(true, result);
    }

    @Test
    void validatePassword() {
        String password = "12345678";
        boolean result = this.userValidator.validatePassword(password);
        Assertions.assertEquals(true, result);
    }

    @Test
    void validateUserData() {
        String name = "Pedro";
        String email = "pedro@gmail.com";
        String password = "123456789";
        String resultMessage = this.userValidator.validateUserData(name, email, password);
        System.out.println(resultMessage);
        Assertions.assertEquals("Usuário cadastrado com sucesso!", resultMessage);
    }
}