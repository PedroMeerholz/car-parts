package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.User;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.NewUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserPasswordDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.UserRepository;
import io.github.pedromeerholz.Car.Parts.Stock.userValidations.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserValidator userValidator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userValidator = new UserValidator();
    }

    public String createUser(NewUserDto newUserDto) {
        String resultMessage = "";
        try {
            resultMessage = this.userValidator.validateUserData(newUserDto.getName(), newUserDto.getEmail(),
                    newUserDto.getPassword());
            if (resultMessage.equals("Usuário cadastrado com sucesso!")) {
                User user = new User();
                user.setName(newUserDto.getName());
                user.setEmail(newUserDto.getEmail());
                user.setPassword(newUserDto.getPassword());
                this.userRepository.save(user);
            }
            return resultMessage;
        } catch (Exception exception) {
            exception.printStackTrace();
            return resultMessage;
        }
    }

    public boolean updateUser(String email, UpdateUserDto updateUserDto) {
        try {
            if (this.userRepository.findByEmail(email).get() != null) {
                User currentUser = this.userRepository.findByEmail(email).get();
                User user = new User();
                user.setId(currentUser.getId());
                user.setName(updateUserDto.getName());
                user.setEmail(updateUserDto.getEmail());
                user.setPassword(currentUser.getPassword());
                this.userRepository.save(user);
                return true;
            }
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
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
