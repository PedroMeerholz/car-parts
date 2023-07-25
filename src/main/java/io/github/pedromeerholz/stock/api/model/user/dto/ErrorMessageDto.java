package io.github.pedromeerholz.stock.api.model.user.dto;

public class ErrorMessageDto extends UserResponseDto {
    private String errorMessage;

    public ErrorMessageDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
