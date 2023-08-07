package io.github.pedromeerholz.stock.api.model.responsesDtos;

public class MessageDto extends ResponseDto {
    private String message;

    public MessageDto(String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}
