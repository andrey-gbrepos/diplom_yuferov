package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.model.API;
import ru.gb.debriefing.model.Planes;
import ru.gb.debriefing.service.PlaneService;

import java.util.List;

@RestController
@RequestMapping("/planes")
@Tag(name = "Plane", description = "API для работы с информацией о самолетах")
public class PlaneController {

    private final PlaneService planeService;

    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get plane",
            description = "Возвращает информацию о самолете по идентификатору")
    public ResponseEntity<Planes> get(@PathVariable @Parameter(description = "Идентификатор самолета") Long id) {
        return planeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping
    @Operation(summary = "Get planes",
            description = "Возвращает информацию по всем самолетам")
    public ResponseEntity<List<Planes>> findAll() {
        return ResponseEntity.ok(planeService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PostMapping("/create")
    @Operation(summary = "Create plane",
            description = "Создает новую позицию самолета (POST Пример:  /plane/create  + BODY)")
    public ResponseEntity<Planes> create(@RequestBody @Parameter(description = "Информация о самолете") Planes plane) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planeService.create(plane).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete plane",
            description = "Удаляет позицию самолета по идентификатору (DELETE  Пример:  /plane/delete/4)")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор самолета") Long id) {
        planeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PutMapping("update/{id}")
    @Operation(summary = "Update plane",
            description = "Обновляет информацию о самолете по идентификатору(PUT  Пример:  /plane/update/2)")
    public ResponseEntity<Planes> update(@PathVariable @Parameter(description = "Идентификатор самолета") Long id, @RequestBody @Parameter(description = "Обновленная информация") Planes plane) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planeService.update(id, plane).get());
    }

}