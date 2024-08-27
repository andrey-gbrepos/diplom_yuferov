package ru.gb.debriefing.responsedto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudByDate {

    private Long id;
    private String name;
    private Long countLand;
    private Long flightTime;
    private LocalDate dat;

}