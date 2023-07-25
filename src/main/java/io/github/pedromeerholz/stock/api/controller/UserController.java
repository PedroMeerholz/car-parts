package io.github.pedromeerholz.stock.api.controller;

import io.github.pedromeerholz.stock.api.model.responsesDtos.ResponseDto;
import io.github.pedromeerholz.stock.api.model.user.dto.*;
import io.github.pedromeerholz.stock.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDto> createUser(@RequestBody NewUserDto newUserDto) {
        return this.userService.createUser(newUserDto);
    }

    @PatchMapping("/update")
    @Operation(summary = "Update the user name and email", method = "PATCH")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody UpdateUserDto updateUserDto, @RequestParam String email, @RequestHeader("Authorization") String authorizationToken) {
        return this.userService.updateUser(email, updateUserDto, authorizationToken);
    }

    @PatchMapping("/updatePassword")
    @Operation(summary = "Update the user password", method = "PATCH")
    public ResponseEntity<ResponseDto> updateUserPassword(@RequestBody UpdateUserPasswordDto updateUserPasswordDto, @RequestParam String email, @RequestHeader("Authorization") String authorizationToken) {
        return this.userService.updateUserPassword(email, updateUserPasswordDto, authorizationToken);
    }

    @GetMapping("/login")
    @Operation(summary = "User Login", method = "GET")
    public ResponseEntity<ResponseDto> login(@RequestParam String email, @RequestHeader("Authorization") String password) {
        return this.userService.login(email, password);
    }
}
