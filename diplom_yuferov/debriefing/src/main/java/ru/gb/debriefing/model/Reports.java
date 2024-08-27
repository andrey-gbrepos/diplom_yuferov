package ru.gb.debriefing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "reports")
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "flight_day")
    private LocalDate flightDay;
    @Column(name = "plane_id")
    private Long planeId;
    @Column(name = "pilot_id")
    private Long pilotId;
    @Column(name = "copilot_id")
    private Long coPilotId;
    @Column(name = "student_id")
    private Long studentId;
    private Long tripCount;
    private Long tripTime;
    private Double engineTime;
    private Long fuelConsumpt;

}