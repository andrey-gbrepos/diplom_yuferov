package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.model.API;
import ru.gb.debriefing.responsedto.UsRolName;
import ru.gb.debriefing.security.UserRole;
import ru.gb.debriefing.service.UserRoleService;

import java.util.List;

@RestController
@RequestMapping("/admin/useroles")
@Tag(name = "UserRoles", description = "API для работы с правами пользователей")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get UserRole",
            description = "Возвращает UserRole по идентификатору")
    public ResponseEntity<UserRole> get(@PathVariable Long id) {
        return userRoleService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping
    @Operation(summary = "Get UserRoles",
            description = "Возвращает все записи UserRole")
    public ResponseEntity<List<UsRolName>> findAll() {
        return ResponseEntity.ok(userRoleService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PostMapping
    @Operation(summary = "Create UserRole",
            description = "Создает UserRole")
    public ResponseEntity<UserRole> create(@RequestBody UserRole useRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.create(useRole).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete UserRoles",
            description = "Удаляет UserRole по идентификатору")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userRoleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PutMapping("/{id}")
    @Operation(summary = "Update UserRoles",
            description = "Обновляет UserRole по идентификатору")
    public ResponseEntity<UserRole> update(@RequestBody UserRole useRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.update(useRole).get());
    }

}
