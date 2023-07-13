package io.github.pedromeerholz.stock.validations;

public class EmptyValueValidator {
    public boolean emptyValueValidation(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        return true;
    }
}
