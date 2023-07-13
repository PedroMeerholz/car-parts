package io.github.pedromeerholz.stock.validations.userValidations;

import io.github.pedromeerholz.stock.api.model.user.User;
import io.github.pedromeerholz.stock.api.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RegisteredEmailValidator {
    private UserRepository userRepository;

    public RegisteredEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isRegisteredEmail(String email) {
        try {
            User user = this.userRepository.findByEmail(email).get();
            if (user == null) {
                return false;
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
