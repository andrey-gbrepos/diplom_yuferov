package ru.gb.debriefing.responsedto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PilotByDate {

    private Long id;
    private String name;
    private Long flightTime;
    private LocalDate dat;

}