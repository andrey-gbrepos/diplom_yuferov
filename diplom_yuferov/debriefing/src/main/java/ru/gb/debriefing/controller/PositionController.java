package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.model.*;
import ru.gb.debriefing.service.*;
import java.util.List;

@RestController
@RequestMapping("/positions")
@Tag(name = "Position", description = "API для работы с должностями")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get position",
            description = "Возвращает название должности по идентификатору")
    public ResponseEntity<Positions> get(@PathVariable @Parameter(description = "Идентификатор должности") Long id) {
        return positionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping
    @Operation(summary = "Get positions",
            description = "Возвращает список должностей")
    public ResponseEntity<List<Positions>> findAll() {
        return ResponseEntity.ok(positionService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PostMapping
    @Operation(summary = "Create position",
            description = "Создает новую позицию должности")
    public ResponseEntity<Positions> create(@RequestBody @Parameter(description = "Новая должность") Positions position) {
        return ResponseEntity.status(HttpStatus.CREATED).body(positionService.create(position).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete position",
            description = "Удаляет позицию должности по идентификатору")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор должности") Long id) {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PutMapping("/{id}")
    @Operation(summary = "Update position",
            description = "Изменяет позицию должности")
    public ResponseEntity<Positions> update(@PathVariable @Parameter(description = "Идентификатор должности") Long id,
                                            @RequestBody @Parameter(description = "Измененная должность") Positions position) {
        return ResponseEntity.status(HttpStatus.CREATED).body(positionService.update(id, position).get());
    }
}