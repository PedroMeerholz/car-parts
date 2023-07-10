package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.User;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.NewUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(NewUserDto newUserDto) {
        System.out.println("Aqui");
        User user = new User();
        user.setName(newUserDto.getUserName());
        user.setEmail(newUserDto.getEmail());
        user.setPassword(newUserDto.getPassword());
        this.userRepository.save(user);
        return true;
    }
}
