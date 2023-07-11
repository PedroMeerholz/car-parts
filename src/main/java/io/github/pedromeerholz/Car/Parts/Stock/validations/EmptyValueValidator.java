package io.github.pedromeerholz.Car.Parts.Stock.validations;

public class EmptyValueValidator {
    public boolean emptyValueValidation(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        return true;
    }
}
