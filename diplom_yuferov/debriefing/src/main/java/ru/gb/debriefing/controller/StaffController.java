package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.model.API;
import ru.gb.debriefing.model.Staff;
import ru.gb.debriefing.responsedto.PilotByDate;
import ru.gb.debriefing.service.StaffService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pilots")
@Tag(name = "Pilots", description = "API для работы с данными по пилотам-инструкторам")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get pilot",
            description = "Возвращает информацию о пилоте-инструкторе по индентификатору")
    public ResponseEntity<Staff> get(@PathVariable @Parameter(description = "Идентификатор пилота-инструктора") Long id) {
        return staffService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping
    @Operation(summary = "Get pilots",
            description = "Возвращает информацию о пилотах-инструкторах")
    public ResponseEntity<List<Staff>> findAll() {
        return ResponseEntity.ok(staffService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PostMapping("/create")
    @Operation(summary = "Create pilot",
            description = "Создает запись о пилоте-инструкторе")
    public ResponseEntity<Staff> create(@RequestBody @Parameter(description = "Информация о пилоте-инструкторе") Staff person) {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.create(person).get());
    }


    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete pilot",
            description = "Удаляет пилота-инструктора по идентификатору")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор пилота-инструктора") Long id) {
        staffService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PutMapping("/update/{id}")
    @Operation(summary = "Update pilot",
            description = "Изменяет информацию о пилоте-инструкторе по индентификатору")
    public ResponseEntity<Staff> update(@PathVariable @Parameter(description = "Идентификатор пилота-инструктора") Long id,
                                        @RequestBody @Parameter(description = "измененная информация о пилоте-инструкторе") Staff pilot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.update(id, pilot).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/bydate")
    @Operation(summary = "Get List By Date",
            description = "Возвращает записи о пилотах-инструкторах по дате полетов " +
                    "(или за предыдущий день, если в запросе дата не указана). " +
                    "Пример запроса: /pilots/bydate?dat=2024-08-12")
    public ResponseEntity<List<PilotByDate>> getPilotsByDate(
            @RequestParam(required = false) @Parameter(description = "Дата полетов yyyy-mm-dd") LocalDate dat) {
        return ResponseEntity.ok(staffService.findPilotsByDate(dat));
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/bydate/{id}")
    @Operation(summary = "Get By Date",
            description = "Возвращает запись о пилоте-инструкторе по идентификтору и по дате полетов " +
                    "(или за предыдущий день, если в запросе дата не указана). " +
                    "Пример запроса: /pilots/bydate/4?dat=2024-08-12")
    public ResponseEntity<PilotByDate> getPilotByDate(
            @PathVariable @Parameter(description = "Идентификатор пилота-инструктора")Long id,
            @RequestParam(required = false) @Parameter(description = "Дата полетов yyyy-mm-dd") LocalDate dat) {
        return staffService.findPilotByDate(id, dat)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/byweek/{id}")
    @Operation(summary = "Get pilot ByWeek",
            description = "Возвращает запись о пилоте-инструкторе по идентификтору за предыдущую неделю")
    public ResponseEntity<List<PilotByDate>> getPilotsByWeek(
            @PathVariable @Parameter(description = "Идентификатор пилота-инструктора")Long id) {
        return ResponseEntity.ok(staffService.findPilotByPeriod(id, "week"));
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/bymonth/{id}")
    @Operation(summary = "Get pilot ByMonth",
            description = "Возвращает запись о пилоте-инструкторе по идентификтору за прошедшие 30 дней")
    public ResponseEntity<List<PilotByDate>> getPilotsByMonth(
            @PathVariable @Parameter(description = "Идентификатор пилота-инструктора")Long id) {
        return ResponseEntity.ok(staffService.findPilotByPeriod(id, "month"));
    }

}
