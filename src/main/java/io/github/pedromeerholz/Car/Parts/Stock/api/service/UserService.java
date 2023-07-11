package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.User;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.NewUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserPasswordDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.UserRepository;
import io.github.pedromeerholz.Car.Parts.Stock.userValidations.RegisteredEmailValidator;
import io.github.pedromeerholz.Car.Parts.Stock.userValidations.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserValidator userValidator;
    private RegisteredEmailValidator registeredEmailValidator;
    private String resultMessage;

    public UserService(UserRepository userRepository, RegisteredEmailValidator registeredEmailValidator) {
        this.userRepository = userRepository;
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
                user.setPassword(newUserDto.getPassword());
                this.userRepository.save(user);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return this.resultMessage;
    }

    public String updateUser(String email, UpdateUserDto updateUserDto) {
        try {
            boolean isRegisteredEmail = this.registeredEmailValidator.isRegisteredEmail(email);
            if (isRegisteredEmail == false) {
                return "O e-mail informado não existe.";
            }
            this.resultMessage = this.userValidator.validateUserDataToUpdate(updateUserDto.getName(),
                    updateUserDto.getEmail());
            if (this.resultMessage.equals("Usuário pode ser atualizado")) {
                User currentUser = this.userRepository.findByEmail(email).get();
                User updatedUser = this.createUpdatedUser(currentUser.getId(), currentUser.getName(),
                        currentUser.getEmail(), currentUser.getPassword());
                this.userRepository.save(updatedUser);
                this.resultMessage = "Usuário atualizado com sucesso!";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return this.resultMessage;
    }

    private User createUpdatedUser(Long id, String name, String email, String password) {
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);
        return updatedUser;
    }

    public boolean updateUserPassword(String email, UpdateUserPasswordDto updateUserPasswordDto) {
        try {
            if (this.userRepository.findByEmail(email).get() != null) {
                User currentUser = this.userRepository.findByEmail(email).get();
                User updatedUser = new User();
                updatedUser.setId(currentUser.getId());
                updatedUser.setName(currentUser.getName());
                updatedUser.setEmail(currentUser.getEmail());
                updatedUser.setPassword(updateUserPasswordDto.getPassword());
                this.userRepository.save(updatedUser);
                return true;
            }
            return false;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean login(String email, String password) {
        try {
            if(this.userRepository.findByEmail(email).get() != null) {
                User credentials = this.userRepository.findByEmail(email).get();
                if (password.equals(credentials.getPassword())) {
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
