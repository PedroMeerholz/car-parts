package io.github.pedromeerholz.Car.Parts.Stock.api.model.dto;

public class NewUserDto {
    private String userName;
    private String email;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
