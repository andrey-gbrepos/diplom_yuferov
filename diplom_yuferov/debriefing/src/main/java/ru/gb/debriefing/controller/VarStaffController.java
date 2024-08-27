package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.model.API;
import ru.gb.debriefing.model.VarStaff;
import ru.gb.debriefing.responsedto.StudByDate;
import ru.gb.debriefing.service.VarStaffService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Students", description = "API для работы с данными по студентам")
public class VarStaffController {

    private final VarStaffService varStaffService;

    public VarStaffController(VarStaffService varStaffService) {
        this.varStaffService = varStaffService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @Operation(summary = "Get student",
            description = "Возвращает информацию о студенте по индентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<VarStaff> get(@PathVariable @Parameter(description = "Идентификатор студента") Long id) {
        return varStaffService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @Operation(summary = "Get students",
            description = "Возвращает информацию о студентах")
    @GetMapping
    public ResponseEntity<List<VarStaff>> findAll() {
        return ResponseEntity.ok(varStaffService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @Operation(summary = "Create student",
            description = "Создает запись о студенте")
    @PostMapping("/create")
    public ResponseEntity<VarStaff> create(@RequestBody VarStaff student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(varStaffService.create(student).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @Operation(summary = "Delete student",
            description = "Удаляет студента по индентификатору")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор студента") Long id) {
        varStaffService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @Operation(summary = "Update student",
            description = "Изменяет информацию о студенте по индентификатору")
    @PutMapping("/update/{id}")
    public ResponseEntity<VarStaff> update(@PathVariable @Parameter(description = "Идентификатор студента") Long id,
                                           @RequestBody @Parameter(description = "Измененные данные о студенте") VarStaff student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(varStaffService.update(id, student).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @Operation(summary = "Get students By Date",
            description = "Возвращает записи о студентах по дате полетов (или за предыдущий день, если в запросе дата не указана) Пример запроса:/students/bydate?dat=2024-08-12")
    @GetMapping("/bydate")
    public ResponseEntity<List<StudByDate>> getRowByDate(
            @RequestParam(required = false) LocalDate dat) {
        return ResponseEntity.ok(varStaffService.findStudsByDay(dat));
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @Operation(summary = "Get By Date",
            description = "Возвращает запись о студенте по идетнтификтору и по дате полетов (или за предыдущий день, если в запросе дата не указана). Пример запроса: /students/bydate/18?dat=2024-08-12")
    @GetMapping("/bydate/{id}")
    public ResponseEntity<StudByDate> getRowByDate(@PathVariable @Parameter(description = "Идентификатор студента") Long id,
                                                   @RequestParam(required = false) @Parameter(description = "Дата полетов") LocalDate dat) {
        return varStaffService.findStudByDate(id, dat)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
