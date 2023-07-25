package io.github.pedromeerholz.stock.api.service;

import io.github.pedromeerholz.stock.api.model.user.User;
import io.github.pedromeerholz.stock.api.model.user.dto.*;
import io.github.pedromeerholz.stock.api.repository.UserRepository;
import io.github.pedromeerholz.stock.security.AuthorizationTokenGenerator;
import io.github.pedromeerholz.stock.validations.AuthorizationTokenValidator;
import io.github.pedromeerholz.stock.validations.userValidations.RegisteredEmailValidator;
import io.github.pedromeerholz.stock.validations.userValidations.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<UserResponseDto> createUser(NewUserDto newUserDto) {
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
                return new ResponseEntity(new AuthorizationTokenDto(authorizationToken), HttpStatus.OK);
            }
            return new ResponseEntity(new ErrorMessageDto("Os dados informados não são válidos"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserResponseDto> updateUser(String email, UpdateUserDto updateUserDto, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            boolean isRegisteredEmail = this.registeredEmailValidator.isRegisteredEmail(email);
            boolean isValidUserData = this.userValidator.validateUserDataToUpdate(updateUserDto.getName(), updateUserDto.getEmail());
            if (isUserAuthorized && isRegisteredEmail && isValidUserData) {
                User currentUser = this.userRepository.findByEmail(email).get();
                User updatedUser = this.createUpdatedUser(currentUser.getId(), updateUserDto.getName(), updateUserDto.getEmail(), currentUser.getPassword(), currentUser.getAuthorizationToken());
                this.userRepository.save(updatedUser);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserResponseDto> updateUserPassword(String email, UpdateUserPasswordDto updateUserPasswordDto, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            boolean isRegisteredEmail = this.registeredEmailValidator.isRegisteredEmail(email);
            boolean isRegisteredUser = this.userRepository.findByEmail(email).get() != null;
            if (isRegisteredEmail && isUserAuthorized && isRegisteredUser) {
                User currentUser = this.userRepository.findByEmail(email).get();
                boolean isValidPassword = this.userValidator.validatePasswordToUpdate(updateUserPasswordDto.getPassword());
                if (isValidPassword) {
                    String newEncodedPassword = this.encoder.encode(updateUserPasswordDto.getPassword());
                    User updatedUser = this.createUpdatedUser(currentUser.getId(), currentUser.getName(), currentUser.getEmail(), newEncodedPassword, currentUser.getAuthorizationToken());
                    this.userRepository.save(updatedUser);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    public ResponseEntity<UserResponseDto> login(String email, String password) {
        try {
            if(this.userRepository.findByEmail(email).get() != null) {
                User credentials = this.userRepository.findByEmail(email).get();
                if (this.userValidator.validateEncodedPassword(this.encoder, password, credentials.getPassword())) {
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ErrorMessageDto("Usuário não cadastrado"), HttpStatus.UNAUTHORIZED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
