package ru.gb.debriefing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.debriefing.model.Reports;
import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Reports, Long> {

    @Query(nativeQuery = true, value = "select distinct student_id  from reports t where t.flight_day = :dat")
    List<Long> findDistinctStudentIdByFlightDay(LocalDate dat);

    @Query(nativeQuery = true, value = "select distinct pilot_id  from reports t where t.flight_day = :dat")
    List<Long> findDistinctPilotIdByFlightDay(LocalDate dat);

    @Query(nativeQuery = true, value = "select distinct copilot_id  from reports t where t.flight_day = :dat")
    List<Long> findDistinctCoPilotIdByFlightDay(LocalDate dat);

    @Query(nativeQuery = true, value = "select distinct plane_id  from reports t where t.flight_day = :dat")
    List<Long> findDistinctPlaneIdByFlightDay(LocalDate dat);

    List<Reports> findByFlightDayBetween(LocalDate start, LocalDate end);

    List<Reports> findByFlightDay(LocalDate dat);

}
