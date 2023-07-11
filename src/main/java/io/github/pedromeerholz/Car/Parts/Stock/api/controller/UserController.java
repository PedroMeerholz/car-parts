package io.github.pedromeerholz.Car.Parts.Stock.api.controller;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.NewUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserPasswordDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody NewUserDto newUserDto) {
        return this.userService.createUser(newUserDto);
    }

    @PatchMapping("/update")
    public HttpStatus updateUser(@RequestBody UpdateUserDto updateUserDto, @RequestParam String email) {
        return this.userService.updateUser(email, updateUserDto);
    }

    @PatchMapping("/updatePassword")
    public HttpStatus updateUserPassword(@RequestBody UpdateUserPasswordDto updateUserPasswordDto, @RequestParam String email) {
        return this.userService.updateUserPassword(email, updateUserPasswordDto);
    }

    @GetMapping("/login")
    public HttpStatus login(@RequestParam String email, @RequestParam String password) {
        return this.userService.login(email, password);
    }
}
