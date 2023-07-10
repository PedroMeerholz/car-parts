package io.github.pedromeerholz.Car.Parts.Stock.api.controller;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.NewUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.dto.UpdateUserPasswordDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.service.UserService;
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
        boolean isUserCreated = this.userService.createUser(newUserDto);
        if (isUserCreated) {
            return "Usuário criado com suscesso";
        }
        return "Não foi possível cadastrar o usuário. Verifique as informações e tente novamente.";
    }

    @PatchMapping("/update")
    public String updateUser(@RequestBody UpdateUserDto updateUserDto, @RequestParam String email) {
        boolean isUserUpdated = this.userService.updateUser(email, updateUserDto);
        if (isUserUpdated) {
            return "Os dados do usuário foram atualizados";
        }
        return "Não foi possível atualizar os dados do usuário. Verifique as informações e tente novamente.";
    }

    @PatchMapping("/updatePassword")
    public String updateUserPassword(@RequestBody UpdateUserPasswordDto updateUserPasswordDto, @RequestParam String email) {
        boolean isUserPasswordUpdated = this.userService.updateUserPassword(email, updateUserPasswordDto);
        if (isUserPasswordUpdated) {
            return "A senha do usuário foi atualizada";
        }
        return "Não foi possível atualizar a senha do usuário. Informe uma senha válida e tente novamente.";
    }

    @GetMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        boolean isUserAuthorized = this.userService.login(email, password);
        if (isUserAuthorized) {
            return "Usuário apto a fazer login";
        }
        return "Verifique as informações e tente novamente";
    }
}
