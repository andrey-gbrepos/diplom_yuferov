package ru.gb.debriefing.service;

import org.springframework.stereotype.Service;
import ru.gb.debriefing.model.Reports;
import ru.gb.debriefing.repository.ReportRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final VarStaffService varStaffService;
    private final PlaneService planeService;

    public ReportService(ReportRepository repository,
                         VarStaffService varStaffService,
                         PlaneService planeService) {
        this.reportRepository = repository;
        this.varStaffService = varStaffService;
        this.planeService = planeService;
    }

    public List<Reports> findAll() {
        return reportRepository.findAll();
    }

    public Optional<Reports> findById(Long id) {
        return reportRepository.findById(id);
    }

    public Optional<Reports> create(Reports report) {
        return Optional.of(reportRepository.save(report));
    }

    public Optional<Reports> update(Long id, Reports report) {
        Optional<Reports> oldReportOpt = reportRepository.findById(id);
        if (oldReportOpt.isPresent()) {
            varStaffService.changeByChgReport(report, oldReportOpt.get());
            planeService.changeByChgReport(report, oldReportOpt.get());
            oldReportOpt.get().setPlaneId(report.getPlaneId());
            oldReportOpt.get().setPilotId(report.getPilotId());
            oldReportOpt.get().setCoPilotId(report.getCoPilotId());
            oldReportOpt.get().setStudentId(report.getStudentId());
            oldReportOpt.get().setFlightDay(report.getFlightDay());
            oldReportOpt.get().setTripTime(report.getTripTime());
            oldReportOpt.get().setTripCount(report.getTripCount());
            oldReportOpt.get().setEngineTime(report.getEngineTime());
            oldReportOpt.get().setFuelConsumpt(report.getFuelConsumpt());
        }
        return oldReportOpt;
    }

    public List<Reports> findReportsByDate(LocalDate date) {
        LocalDate dat = Objects.isNull(date) ? LocalDate.now().minusDays(1L) : date;
        List<Reports> allRepoByDate = reportRepository.findByFlightDay(dat);
        return allRepoByDate;
    }

    public void delete(Long id) {
        Optional<Reports> reportOpt = reportRepository.findById(id);
        if (reportOpt.isPresent()) {
            varStaffService.changeByDelReport(reportOpt.get());
            planeService.changeByDelReport(reportOpt.get());
        }
        reportRepository.deleteById(id);
    }

}
