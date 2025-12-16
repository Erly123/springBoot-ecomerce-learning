package com.chl.ecomerce.controller;

import com.chl.ecomerce.controller.dto.CreateUserDto;
import com.chl.ecomerce.entities.UserEntity;
import com.chl.ecomerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
@RestController
@RequestMapping(path = "/users")
@Tag(
        name = "Usuários",
        description = "Cadastro, consulta e remoção de usuários"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Criar um novo usuário",
            description = "Realiza o cadastro de um novo usuário no sistema"
    )
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto dto) {

        var user = userService.createUser(dto);

        return ResponseEntity.created(URI.create("/users/" + user.getUserId())).build();
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Consulta os dados de um usuário a partir do seu identificador"
    )
    public ResponseEntity<UserEntity> findById(@PathVariable("userId") UUID userId) {

        var user = userService.findById(userId);
        return user.isPresent() ?
                ResponseEntity.ok(user.get()) :
                ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Remover usuário",
            description = "Remove um usuário do sistema com base no seu identificador"
    )
    public ResponseEntity<Void> deleteById(@PathVariable("userId") UUID userId) {

        var deleted = userService.deletedById(userId);
        return deleted ?
                ResponseEntity.noContent().build():
                ResponseEntity.notFound().build();
    }
}
