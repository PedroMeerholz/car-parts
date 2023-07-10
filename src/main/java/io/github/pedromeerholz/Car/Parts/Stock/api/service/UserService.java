package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.User;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.NewUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(NewUserDto newUserDto) {
        try {
            User user = new User();
            user.setName(newUserDto.getUserName());
            user.setEmail(newUserDto.getEmail());
            user.setPassword(newUserDto.getPassword());
            this.userRepository.save(user);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(String email, UpdateUserDto updateUserDto) {
        try {
            if (this.userRepository.findByEmail(email).get() != null) {
                User currentUser = this.userRepository.findByEmail(email).get();
                System.out.println("Aqui chega");
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
}
