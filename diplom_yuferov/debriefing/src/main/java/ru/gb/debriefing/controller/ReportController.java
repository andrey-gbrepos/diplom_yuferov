package ru.gb.debriefing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.debriefing.model.*;
import ru.gb.debriefing.service.ReportService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@Tag(name = "Report", description = "API для работы с отчетами")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get report",
            description = "Возвращает отчет по идентификатору")
    public ResponseEntity<Reports> get(@PathVariable @Parameter(description = "Идентификатор отчета") Long id) {
        return reportService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping
    @Operation(summary = "Get reports",
            description = "Возвращает весь список отчетов")
    public ResponseEntity<List<Reports>> findAll() {
        return ResponseEntity.ok(reportService.findAll());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PostMapping("/create")
    @Operation(summary = "Create report",
            description = "Создает отчет")
    public ResponseEntity<Reports> create(@RequestBody @Parameter(description = "Тело отчета") Reports report) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.create(report).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удаляет отчет",
            description = "Возвращает информацию о роли по идентификатору учетной записи")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор отчета") Long id) {
        reportService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @PutMapping("/update/{id}")
    @Operation(summary = "Изменяет отчет",
            description = "Возвращает информацию о роли по идентификатору учетной записи")
    public ResponseEntity<Reports> update(@PathVariable @Parameter(description = "Идентификатор отчета") Long id,
                                          @RequestBody @Parameter(description = "Тело отчета") Reports report) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.update(id, report).get());
    }

    @API.SuccessfulResponse
    @API.NotFoundResponse
    @API.ServerErrorResponse
    @GetMapping("/bydate")
    @Operation(summary = "Get reports by date",
            description = "Возвращает отчеты по дате полетов")
    public ResponseEntity<List<Reports>> getRowByDate(
            @RequestParam(required = false) @Parameter(description = "Дата полетов yyyy-mm-dd") LocalDate dat) {
        return ResponseEntity.ok(reportService.findReportsByDate(dat));
    }

}