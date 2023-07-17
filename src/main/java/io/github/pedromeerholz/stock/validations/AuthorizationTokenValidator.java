package io.github.pedromeerholz.stock.validations;

import io.github.pedromeerholz.stock.api.model.user.User;
import io.github.pedromeerholz.stock.api.repository.UserRepository;

import java.util.Optional;

public class AuthorizationTokenValidator {
    public boolean validateAuthorizationToken(UserRepository userRepository, String email, String receivedToken) {
        String registeredToken = this.getRegisteredToken(userRepository, email);
        if (receivedToken.equals(registeredToken)) {
            return true;
        }
        return false;
    }

    private String getRegisteredToken(UserRepository userRepository, String email) {
        String token = null;
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            token = user.getAuthorizationToken();
        }
        return token;
    }
}
