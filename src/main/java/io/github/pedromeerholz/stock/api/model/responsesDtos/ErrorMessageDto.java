package io.github.pedromeerholz.stock.api.model.responsesDtos;

public class ErrorMessageDto extends ResponseDto {
    private String errorMessage;

    public ErrorMessageDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
