package ru.gb.debriefing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "varstaff")
public class VarStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column
    private Long id;
    private String name;
    private LocalDate startPractice;
    private LocalDate finishPractice;
    private Long flightTime;

}