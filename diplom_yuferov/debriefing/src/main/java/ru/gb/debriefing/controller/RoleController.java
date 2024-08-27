package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.security.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import ru.gb.debriefing.model.*;
import java.util.List;
import ru.gb.debriefing.service.RoleService;


@RestController
@RequestMapping("/admin/roles")
@Tag(name = "Roles", description = "API для работы с ролями доступа")
public class RoleController {
    private  final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get role",
            description = "Возвращает роль по идентификатору")
    public ResponseEntity<Role> get(@PathVariable @Parameter(description = "Идентификатор роли") Long id) {
        return roleService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping
    @Operation(summary = "Get roles",
            description = "Возвращает информацию о ролях")
    public ResponseEntity<List<Role>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PostMapping
    @Operation(summary = "Create role",
            description = "Создает новую роль")
    public ResponseEntity<Role> create(@RequestBody @Parameter(description = "Новая роль") Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(role).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete role",
            description = "Удаляет роль по идентификатору")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор роли") Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PutMapping("/{id}")
    @Operation(summary = "update role",
            description = "Обновляет роль по идентификатору")
    public ResponseEntity<Role> update(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.update(role).get());
    }

}
