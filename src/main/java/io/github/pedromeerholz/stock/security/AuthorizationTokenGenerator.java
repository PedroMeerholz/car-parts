package io.github.pedromeerholz.stock.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class AuthorizationTokenGenerator {
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public AuthorizationTokenGenerator() throws NoSuchAlgorithmException {
    }

    public String generateAuthorizationToken(String name, String email) {
        Date registerDate = new Date();
        String stringRegisterDate = registerDate.toString();

        String token = name + "." + stringRegisterDate + "." + email;
        byte[] encodedToken = digest.digest(token.getBytes());

        System.out.println(encodedToken.toString());
        return encodedToken.toString();
    }
}
