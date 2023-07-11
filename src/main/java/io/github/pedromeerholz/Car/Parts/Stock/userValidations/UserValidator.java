package io.github.pedromeerholz.Car.Parts.Stock.userValidations;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public boolean emptyValueValidation(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email) {
        boolean isNotEmptyEmail = this.emptyValueValidation(email);
        boolean isValidEmail = this.isValidEmail(email);
        if (!isNotEmptyEmail || !isValidEmail) {
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        boolean result = matcher.matches();
        return result;
    }

    public boolean validatePasswordPattern(String password) {
        boolean isNotEmptyPassword = this.emptyValueValidation(password);
        boolean isValidPassword = this.validatePasswordLength(password);
        if (!isNotEmptyPassword || !isValidPassword) {
            return false;
        }
        return true;
    }

    private boolean validatePasswordLength(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    public boolean validatePasswordToUpdate(String password) {
        boolean validPassword = this.validatePasswordPattern(password);
        if (!validPassword) {
            return false;
        }
        return true;
    }

    public boolean validateEncodedPassword(BCryptPasswordEncoder encoder, String receivedPassword, String encodedPassword) {
        boolean isPasswordMatches = encoder.matches(receivedPassword, encodedPassword);
        if (!isPasswordMatches) {
            return false;
        }
        return true;
    }

    public String validateUserDataToCreate(String name, String email, String password) {
        if (!this.emptyValueValidation(name)) {
            return "Favor preencher seu nome corretamente.";
        }
        if (!this.validateEmail(email)) {
            return "Preencha um e-mail válido.";
        }
        if (!this.validatePasswordPattern(password)) {
            return "Preencha uma senha válida. A senha deve possuir no mínimo oito caracteres.";
        }
        return "Usuário cadastrado com sucesso!";
    }

    public boolean validateUserDataToUpdate(String name, String email) {
        if (!this.emptyValueValidation(name) || !this.validateEmail(email)) {
            return false;
        }
        return true;
    }
}
