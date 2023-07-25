package io.github.pedromeerholz.stock.api.model.responsesDtos;

public class AuthorizationTokenDto extends ResponseDto {
    private String authorizationToken;

    public AuthorizationTokenDto(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }
}
