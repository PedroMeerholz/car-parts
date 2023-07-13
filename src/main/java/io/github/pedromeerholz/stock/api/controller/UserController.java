package io.github.pedromeerholz.stock.api.controller;

import io.github.pedromeerholz.stock.api.model.user.dto.NewUserDto;
import io.github.pedromeerholz.stock.api.model.user.dto.UpdateUserDto;
import io.github.pedromeerholz.stock.api.model.user.dto.UpdateUserPasswordDto;
import io.github.pedromeerholz.stock.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Register new users", method = "POST")
    public HttpStatus createUser(@RequestBody NewUserDto newUserDto) {
        return this.userService.createUser(newUserDto);
    }

    @PatchMapping("/update")
    @Operation(summary = "Update the user name and email", method = "PATCH")
    public HttpStatus updateUser(@RequestBody UpdateUserDto updateUserDto, @RequestParam String email) {
        return this.userService.updateUser(email, updateUserDto);
    }

    @PatchMapping("/updatePassword")
    @Operation(summary = "Update the user password", method = "PATCH")
    public HttpStatus updateUserPassword(@RequestBody UpdateUserPasswordDto updateUserPasswordDto, @RequestParam String email) {
        return this.userService.updateUserPassword(email, updateUserPasswordDto);
    }

    @GetMapping("/login")
    @Operation(summary = "User Login", method = "GET")
    public HttpStatus login(@RequestParam String email, @RequestParam String password) {
        return this.userService.login(email, password);
    }
}
