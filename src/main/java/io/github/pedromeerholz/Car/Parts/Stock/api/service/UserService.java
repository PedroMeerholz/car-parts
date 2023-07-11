package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.User;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.NewUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserPasswordDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.UserRepository;
import io.github.pedromeerholz.Car.Parts.Stock.userValidations.RegisteredEmailValidator;
import io.github.pedromeerholz.Car.Parts.Stock.userValidations.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserValidator userValidator;
    private RegisteredEmailValidator registeredEmailValidator;
    private BCryptPasswordEncoder encoder;
    private String resultMessage;

    public UserService(UserRepository userRepository, RegisteredEmailValidator registeredEmailValidator, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userValidator = new UserValidator();
        this.registeredEmailValidator = registeredEmailValidator;
    }

    public String createUser(NewUserDto newUserDto) {
        this.resultMessage = this.userValidator.validateUserDataToCreate(newUserDto.getName(), newUserDto.getEmail(),
                newUserDto.getPassword());
        try {
            if (this.resultMessage.equals("Usuário cadastrado com sucesso!")) {
                User user = new User();
                user.setName(newUserDto.getName());
                user.setEmail(newUserDto.getEmail());
                String encodedPassword = encoder.encode(newUserDto.getPassword());
                user.setPassword(encodedPassword);
                this.userRepository.save(user);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return this.resultMessage;
    }

    public HttpStatus updateUser(String email, UpdateUserDto updateUserDto) {
        try {
            boolean isRegisteredEmail = this.registeredEmailValidator.isRegisteredEmail(email);
            if (isRegisteredEmail == false) {
                return HttpStatus.UNAUTHORIZED;
            }
            boolean isValidUserData = this.userValidator.validateUserDataToUpdate(updateUserDto.getName(),
                    updateUserDto.getEmail());
            if (isValidUserData) {
                User currentUser = this.userRepository.findByEmail(email).get();
                User updatedUser = this.createUpdatedUser(currentUser.getId(), updateUserDto.getName(),
                        updateUserDto.getEmail(), currentUser.getPassword());
                this.userRepository.save(updatedUser);
                return HttpStatus.ACCEPTED;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus updateUserPassword(String email, UpdateUserPasswordDto updateUserPasswordDto) {
        try {
            boolean isRegisteredEmail = this.registeredEmailValidator.isRegisteredEmail(email);
            if (!isRegisteredEmail) {
                return HttpStatus.UNAUTHORIZED;
            }
            if (this.userRepository.findByEmail(email).get() != null) {
                User currentUser = this.userRepository.findByEmail(email).get();
                boolean isValidPassword = this.userValidator.validatePasswordToUpdate(updateUserPasswordDto.getPassword());
                if (isValidPassword) {
                    String newEncodedPassword = this.encoder.encode(updateUserPasswordDto.getPassword());
                    User updatedUser = this.createUpdatedUser(currentUser.getId(), currentUser.getName(), currentUser.getEmail(), newEncodedPassword);
                    this.userRepository.save(updatedUser);
                    return HttpStatus.ACCEPTED;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return HttpStatus.UNAUTHORIZED;
    }

    private User createUpdatedUser(Long id, String name, String email, String password) {
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);
        return updatedUser;
    }

    public HttpStatus login(String email, String password) {
        try {
            if(this.userRepository.findByEmail(email).get() != null) {
                User credentials = this.userRepository.findByEmail(email).get();
                if (!this.userValidator.validateEncodedPassword(this.encoder, password, credentials.getPassword())) {
                    return HttpStatus.UNAUTHORIZED;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return HttpStatus.ACCEPTED;
    }
}
