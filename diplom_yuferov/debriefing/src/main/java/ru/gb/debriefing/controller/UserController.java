package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.model.API;
import ru.gb.debriefing.security.User;
import ru.gb.debriefing.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Tag(name = "Users", description = "API для работы с учетными записями пользователей")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get user",
            description = "Возвращает учетную запись по идентификатору")
    public ResponseEntity<User> get(@PathVariable @Parameter(description = "Идентификатор логина") Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping
    @Operation(summary = "Get users",
            description = "Возвращает все учетные записи")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PostMapping
    @Operation(summary = "Create user",
            description = "Создает учетную запись")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user",
            description = "Удаляет учетную запись")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PutMapping("/{id}")
    @Operation(summary = "Update user",
            description = "Обновляет учетную запись")
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.update(user).get());
    }

}
