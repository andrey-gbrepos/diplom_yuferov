package ru.gb.debriefing.service;

import org.springframework.stereotype.Service;
import ru.gb.debriefing.model.Reports;
import ru.gb.debriefing.model.VarStaff;
import ru.gb.debriefing.repository.ReportRepository;
import ru.gb.debriefing.repository.VarStaffRepository;
import ru.gb.debriefing.responsedto.StudByDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class VarStaffService {
    private final VarStaffRepository varStaffRepository;
    private final ReportRepository reportRepository;

    public VarStaffService(VarStaffRepository varStaffRepository, ReportRepository reportRepository) {
        this.varStaffRepository = varStaffRepository;
        this.reportRepository = reportRepository;
    }

    public List<VarStaff> findAll() {
        return varStaffRepository.findAll();
    }

    public Optional<VarStaff> findById(Long id) {
        return varStaffRepository.findById(id);
    }

    public Optional<VarStaff> create(VarStaff student) {
        return Optional.of(varStaffRepository.save(student));
    }

    public void delete(Long id) {
        varStaffRepository.deleteById(id);
    }

    public Optional<VarStaff> update(Long id, VarStaff student) {
        Optional<VarStaff> stud = varStaffRepository.findById(id);
        if (stud.isPresent()) {
            stud.get().setStartPractice(student.getStartPractice());
            stud.get().setFinishPractice(student.getFinishPractice());
            stud.get().setFlightTime(student.getFlightTime());
        }
        return stud;
    }

    /**
     * Вызывается из ReportServis при изменении записей в таблице Reports
     *
     * @param report
     */
    public void changeByReport(Reports report) {
        Optional<VarStaff> studOpt = varStaffRepository.findById(report.getStudentId());
        if (studOpt.isPresent()) {
            studOpt.get().setFlightTime(studOpt.get().getFlightTime() + report.getTripTime());
        }
    }

    /**
     * Вызывается из ReportServis при изменении записей в таблице Reports
     *
     * @param report
     */
    public void changeByDelReport(Reports report) {
        Optional<VarStaff> studOpt = varStaffRepository.findById(report.getStudentId());
        if (studOpt.isPresent()) {
            studOpt.get().setFlightTime(studOpt.get().getFlightTime() - report.getTripTime());
        }
    }

    /**
     * Вызывается из ReportServis при изменении записей в таблице Reports
     *
     * @param newReport
     * @param oldReport
     */
    public void changeByChgReport(Reports newReport, Reports oldReport) {
        Optional<VarStaff> studOpt = varStaffRepository.findById(newReport.getStudentId());
        if (studOpt.isPresent()) {
            studOpt.get().setFlightTime(
                    studOpt.get().getFlightTime()
                            - oldReport.getTripTime()
                            + newReport.getTripTime()
            );
        }
    }

    public List<StudByDate> findStudsByDay(LocalDate date) {
        LocalDate dat = Objects.isNull(date) ? LocalDate.now().minusDays(1L) : date;
        List<Long> listStudId = reportRepository.findDistinctStudentIdByFlightDay(dat);
        List<Reports> allRepo = reportRepository.findByFlightDay(dat);
        List<StudByDate> listStudByDate = new ArrayList<>();
        for (Long it : listStudId) {
            StudByDate studByDate = new StudByDate();
            studByDate.setId(it);
            studByDate.setDat(dat);
            listStudByDate.add(fillStudByDate(studByDate,
                    allRepo.stream()
                            .filter(i -> i.getStudentId() == it)
                            .collect(Collectors.toList())
            ).get());
        }
        return listStudByDate;
    }


    public Optional<StudByDate> findStudByDate(Long studId, LocalDate date) {
        LocalDate dat = Objects.isNull(date) ? LocalDate.now().minusDays(1L) : date;
        StudByDate studByDate = new StudByDate();
        studByDate.setId(studId);
        studByDate.setDat(dat);
        List<Reports> allRepo = reportRepository.findByFlightDay(dat);
        List<Reports> studRepo = allRepo.stream()
                .filter(it -> it.getStudentId() == studId)
                .collect(Collectors.toList());
        return fillStudByDate(studByDate, studRepo);
    }

    private final Optional<StudByDate> fillStudByDate(StudByDate studByDate, List<Reports> lst) {
        studByDate.setName(varStaffRepository.findById(studByDate.getId()).get().getName());
        studByDate.setCountLand(lst.stream()
                .mapToLong(it -> it.getTripCount()).sum());
        studByDate.setFlightTime(lst.stream()
                .mapToLong(it -> it.getTripTime()).sum());
        return Optional.of(studByDate);
    }
}