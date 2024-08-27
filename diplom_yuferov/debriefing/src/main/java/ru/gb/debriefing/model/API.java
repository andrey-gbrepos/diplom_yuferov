package ru.gb.debriefing.model;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.gb.debriefing.responsedto.StudByDate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class API {

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Данные не найдены", responseCode = "404", content = @Content(schema = @Schema(implementation = void.class)))
    public @interface NotFoundResponse {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = StudByDate.class)))
    public @interface SuccessfulResponse {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Ошибка сервера", responseCode = "500", content = @Content(schema = @Schema(implementation = void.class)))
    public @interface ServerErrorResponse {
    }

}
