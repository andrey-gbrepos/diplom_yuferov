package ru.gb.debriefing.service;

import org.springframework.stereotype.Service;
import ru.gb.debriefing.model.Planes;
import ru.gb.debriefing.model.Reports;
import ru.gb.debriefing.repository.PlaneRepository;
import ru.gb.debriefing.repository.ReportRepository;
import ru.gb.debriefing.responsedto.PlaneByDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaneService {

    private final PlaneRepository planeRepository;
    private final ReportRepository reportRepository;

    public PlaneService(PlaneRepository repository, ReportRepository reportRepository) {
        this.planeRepository = repository;
        this.reportRepository = reportRepository;
    }

    public List<Planes> findAll() {
        return planeRepository.findAll();
    }

    public Optional<Planes> findById(Long id) {
        return planeRepository.findById(id);
    }

    public Optional<Planes> create(Planes plane) {
        return Optional.of(planeRepository.save(plane));
    }

    public void delete(Long id) {
        planeRepository.deleteById(id);
    }

    public Optional<Planes> update(Long id, Planes pln) {
        Optional<Planes> plane = planeRepository.findById(id);
        if (plane.isPresent()) {
            plane.get().setPlaneNumber(pln.getPlaneNumber());
            plane.get().setEngineTime(pln.getEngineTime());
            plane.get().setCountLand(pln.getCountLand());
        }
        return plane;
    }

    public void changeByReport(Reports report) {
        Optional<Planes> planeOpt = planeRepository.findById(report.getPlaneId());
        if (planeOpt.isPresent()) {
            planeOpt.get().setCountLand(planeOpt.get().getCountLand() + report.getTripCount());
            planeOpt.get().setEngineTime(planeOpt.get().getEngineTime() + report.getEngineTime());
        }
    }

    public void changeByDelReport(Reports report) {
        Optional<Planes> planeOpt = planeRepository.findById(report.getPlaneId());
        if (planeOpt.isPresent()) {
            planeOpt.get().setCountLand(planeOpt.get().getCountLand() - report.getTripCount());
            planeOpt.get().setEngineTime(planeOpt.get().getEngineTime() - report.getEngineTime());
        }
    }

    public void changeByChgReport(Reports newReport, Reports oldReport) {
        Optional<Planes> planeOpt = planeRepository.findById(newReport.getPlaneId());
        if (planeOpt.isPresent()) {
            planeOpt.get().setCountLand(planeOpt.get().getCountLand()
                    - oldReport.getTripCount()
                    + newReport.getTripCount()
            );
            planeOpt.get().setEngineTime(planeOpt.get().getEngineTime()
                    - oldReport.getEngineTime()
                    + newReport.getEngineTime()
            );
        }
    }

    public List<PlaneByDate> findPlanesByDate(LocalDate date) {
        LocalDate dat = Objects.isNull(date) ? LocalDate.now().minusDays(1L) : date;
        List<Long> listPlaneId = reportRepository.findDistinctPlaneIdByFlightDay(dat);
        List<Reports> allRepo = reportRepository.findByFlightDay(dat);
        List<PlaneByDate> listPlaneByDate = new ArrayList<>();
        for (Long it : listPlaneId) {
            PlaneByDate planeByDate = new PlaneByDate();
            planeByDate.setId(it);
            planeByDate.setDat(dat);
            listPlaneByDate.add(fillStudByDate(planeByDate,
                    allRepo.stream()
                            .filter(i -> i.getStudentId() == it)
                            .collect(Collectors.toList())
            ).get());
        }
        return listPlaneByDate;
    }

    public Optional<PlaneByDate> findPlaneByDate(Long planeId, LocalDate date) {
        LocalDate dat = Objects.isNull(date) ? LocalDate.now().minusDays(1L) : date;
        PlaneByDate planeByDate = new PlaneByDate();
        planeByDate.setId(planeId);
        planeByDate.setDat(dat);
        List<Reports> allRepo = reportRepository.findByFlightDay(dat);
        List<Reports> planeRepo = allRepo.stream()
                .filter(it -> it.getStudentId() == planeId)
                .collect(Collectors.toList());
        return fillStudByDate(planeByDate, planeRepo);
    }

    private final Optional<PlaneByDate> fillStudByDate(PlaneByDate planeByDate, List<Reports> lst) {
        planeByDate.setBoard(planeRepository.findById(planeByDate.getId()).get().getPlaneNumber());
        planeByDate.setCountLand(lst.stream()
                .mapToLong(it -> it.getTripCount()).sum());
        planeByDate.setFlightTime(lst.stream()
                .mapToLong(it -> it.getTripTime()).sum());
        planeByDate.setFuelConsumpt(lst.stream()
                .mapToLong(it -> it.getFuelConsumpt()).sum());
        planeByDate.setEngineTime(lst.stream()
                .mapToDouble(it -> it.getEngineTime()).sum());
        return Optional.of(planeByDate);
    }

}