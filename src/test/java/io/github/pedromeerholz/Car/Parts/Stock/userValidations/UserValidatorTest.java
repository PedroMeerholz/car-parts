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
    void validateUserDataToCreate() {
        String name = "Pedro";
        String email = "pedro@gmail.com";
        String password = "123456789";
        String resultMessage = this.userValidator.validateUserDataToCreate(name, email, password);
        Assertions.assertEquals("Usuário cadastrado com sucesso!", resultMessage);
    }

    @Test
    void validadeUserDataToUpdate() {
        String name = "Pedro";
        String email = "pedro@gmail.com";
        String resultMessage = this.userValidator.validateUserDataToUpdate(name, email);
        Assertions.assertEquals("Usuário atualizado com sucesso!", resultMessage);
    }

    @Test
    void validatePasswordToUpdate() {
        String password = "12345678";
        String resultMessage = this.userValidator.validatePasswordToUpdate(password);
        Assertions.assertEquals("Senha pode ser alterada", resultMessage);
    }
}