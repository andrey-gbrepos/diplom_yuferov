package ru.gb.debriefing.responsedto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlaneByDate {

    private Long id;
    private String board;
    private Long countLand;
    private Long flightTime;
    private LocalDate dat;
    private Long fuelConsumpt;
    private Double engineTime;

}
