package io.github.pedromeerholz.stock.api.service;

import io.github.pedromeerholz.stock.api.model.user.User;
import io.github.pedromeerholz.stock.api.model.user.dto.NewUserDto;
import io.github.pedromeerholz.stock.api.model.user.dto.UpdateUserDto;
import io.github.pedromeerholz.stock.api.model.user.dto.UpdateUserPasswordDto;
import io.github.pedromeerholz.stock.api.repository.UserRepository;
import io.github.pedromeerholz.stock.security.AuthorizationTokenGenerator;
import io.github.pedromeerholz.stock.validations.AuthorizationTokenValidator;
import io.github.pedromeerholz.stock.validations.userValidations.RegisteredEmailValidator;
import io.github.pedromeerholz.stock.validations.userValidations.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final RegisteredEmailValidator registeredEmailValidator;
    private final BCryptPasswordEncoder encoder;
    private final AuthorizationTokenGenerator authorizationTokenGenerator;
    private final AuthorizationTokenValidator authorizationTokenValidator;

    public UserService(UserRepository userRepository, RegisteredEmailValidator registeredEmailValidator)
            throws NoSuchAlgorithmException {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
        this.userValidator = new UserValidator();
        this.registeredEmailValidator = registeredEmailValidator;
        this.authorizationTokenGenerator = new AuthorizationTokenGenerator();
        this.authorizationTokenValidator = new AuthorizationTokenValidator();
    }

    public HttpStatus createUser(NewUserDto newUserDto) {
        try {
            boolean isValidUserData = this.userValidator.validateUserDataToCreate(newUserDto.getName(),
                    newUserDto.getEmail(), newUserDto.getPassword());
            if (isValidUserData) {
                User user = new User();
                user.setName(newUserDto.getName());
                user.setEmail(newUserDto.getEmail());
                String encodedPassword = this.encoder.encode(newUserDto.getPassword());
                user.setPassword(encodedPassword);
                String authorizationToken = this.authorizationTokenGenerator.generateAuthorizationToken(
                        newUserDto.getName(), newUserDto.getEmail());
                user.setAuthorizationToken(authorizationToken);
                this.userRepository.save(user);
                return HttpStatus.ACCEPTED;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus updateUser(String email, UpdateUserDto updateUserDto, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            if (isUserAuthorized == false) {
                return HttpStatus.UNAUTHORIZED;
            }
            boolean isRegisteredEmail = this.registeredEmailValidator.isRegisteredEmail(email);
            if (isRegisteredEmail == false) {
                return HttpStatus.UNAUTHORIZED;
            }
            boolean isValidUserData = this.userValidator.validateUserDataToUpdate(updateUserDto.getName(), updateUserDto.getEmail());
            if (isValidUserData) {
                User currentUser = this.userRepository.findByEmail(email).get();
                User updatedUser = this.createUpdatedUser(currentUser.getId(), updateUserDto.getName(), updateUserDto.getEmail(), currentUser.getPassword(), currentUser.getAuthorizationToken());
                this.userRepository.save(updatedUser);
                return HttpStatus.ACCEPTED;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus updateUserPassword(String email, UpdateUserPasswordDto updateUserPasswordDto, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            if (isUserAuthorized == false) {
                return HttpStatus.UNAUTHORIZED;
            }
            boolean isRegisteredEmail = this.registeredEmailValidator.isRegisteredEmail(email);
            if (!isRegisteredEmail) {
                return HttpStatus.UNAUTHORIZED;
            }
            if (this.userRepository.findByEmail(email).get() != null) {
                User currentUser = this.userRepository.findByEmail(email).get();
                boolean isValidPassword = this.userValidator.validatePasswordToUpdate(updateUserPasswordDto.getPassword());
                if (isValidPassword) {
                    String newEncodedPassword = this.encoder.encode(updateUserPasswordDto.getPassword());
                    User updatedUser = this.createUpdatedUser(currentUser.getId(), currentUser.getName(), currentUser.getEmail(), newEncodedPassword, currentUser.getAuthorizationToken());
                    this.userRepository.save(updatedUser);
                    return HttpStatus.ACCEPTED;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return HttpStatus.UNAUTHORIZED;
    }

    private User createUpdatedUser(Long id, String name, String email, String password, String authorizationToken) {
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        updatedUser.setAuthorizationToken(authorizationToken);
        updatedUser.setPassword(password);
        return updatedUser;
    }

    public HttpStatus login(String email, String password) {
        try {
            if(this.userRepository.findByEmail(email).get() != null) {
                User credentials = this.userRepository.findByEmail(email).get();
                if (this.userValidator.validateEncodedPassword(this.encoder, password, credentials.getPassword())) {
                    return HttpStatus.ACCEPTED;
                }
            }
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.UNAUTHORIZED;
    }
}
