package ru.gb.debriefing.service;

import org.springframework.stereotype.Service;
import ru.gb.debriefing.model.Reports;
import ru.gb.debriefing.model.Staff;
import ru.gb.debriefing.repository.ReportRepository;
import ru.gb.debriefing.repository.StaffRepository;
import ru.gb.debriefing.responsedto.PilotByDate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final ReportRepository reportRepository;

    public StaffService(StaffRepository staffRepository, ReportRepository reportRepository) {
        this.staffRepository = staffRepository;
        this.reportRepository = reportRepository;
    }

    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    public Optional<Staff> create(Staff person) {
        return Optional.of(staffRepository.save(person));
    }

    public void delete(Long id) {
        staffRepository.deleteById(id);
    }

    public Optional<Staff> update(Long id, Staff plt) {
        Optional<Staff> pilot = staffRepository.findById(id);
        if (pilot.isPresent()) {
            pilot.get().setName(plt.getName());
            pilot.get().setPositionId(plt.getPositionId());
            pilot.get().setExperience(plt.getExperience());
        }
        return pilot;
    }

    public List<PilotByDate> findPilotsByDate(LocalDate date) {
        LocalDate dat = Objects.isNull(date) ? LocalDate.now().minusDays(1L) : date;
        List<Reports> allRepo = reportRepository.findByFlightDay(dat);
        List<Long> listFlightStaffId = getListPilotsIdByDate(dat);
        List<PilotByDate> listPilotByDate = new ArrayList<>();
        for (Long it : listFlightStaffId) {
            PilotByDate pilotByDate = new PilotByDate();
            pilotByDate.setId(it);
            pilotByDate.setDat(dat);
            listPilotByDate.add(fillPilotByDate(pilotByDate,
                    allRepo.stream()
                            .filter(i -> i.getPilotId() == it || i.getCoPilotId() == it)
                            .collect(Collectors.toList())
            ).get());
        }
        return listPilotByDate;
    }

    public List<PilotByDate> findPilotByPeriod(Long pilotId, String period) {
        LocalDate datStart;
        LocalDate datEnd = LocalDate.now().minusDays(1L);
        switch (period) {
            case "week":
                datStart = LocalDate.now().minusDays(8L);
                break;
            case "month":
                datStart = LocalDate.now().minusDays(31L);
                break;
            default:
                datStart = LocalDate.now().minusDays(8L);
                break;
        }
        List<Reports> allRepoBetween = reportRepository.findByFlightDayBetween(datStart, datEnd);
        List<Reports> pilotRepo = allRepoBetween.stream()
                .filter(it -> it.getPilotId() == pilotId || it.getCoPilotId() == pilotId)
                .collect(Collectors.toList());
        List<PilotByDate> listPilotByDate = new ArrayList<>();
        List<LocalDate> dates = pilotRepo.stream().map(it -> it.getFlightDay()).distinct().toList();
        List<Reports> pilotRepoByDat = new ArrayList<>();
        for (LocalDate dat : dates) {
            PilotByDate pilotByDate = new PilotByDate();
            pilotByDate.setName(staffRepository.findById(pilotId).get().getName());
            pilotByDate.setId(pilotId);
            pilotByDate.setDat(dat);
            pilotByDate.setFlightTime(pilotRepo.stream()
                    .filter(it -> it.getFlightDay().equals(dat))
                    .mapToLong(it -> it.getTripTime()).sum());
            listPilotByDate.add(pilotByDate);
        }
        return listPilotByDate;
    }

    public Optional<PilotByDate> findPilotByDate(Long pilotId, LocalDate date) {
        LocalDate dat = Objects.isNull(date) ? LocalDate.now().minusDays(1L) : date;
        PilotByDate pilotByDate = new PilotByDate();
        pilotByDate.setId(pilotId);
        pilotByDate.setDat(dat);
        List<Reports> allRepo = reportRepository.findByFlightDay(dat);
        List<Reports> pilotRepo = allRepo.stream()
                .filter(it -> it.getPilotId() == pilotId || it.getCoPilotId() == pilotId)
                .collect(Collectors.toList());
        return fillPilotByDate(pilotByDate, pilotRepo);
    }

    private final Optional<PilotByDate> fillPilotByDate(PilotByDate pilotByDate, List<Reports> lst) {
        pilotByDate.setName(staffRepository.findById(pilotByDate.getId()).get().getName());
        pilotByDate.setFlightTime(lst.stream()
                .mapToLong(it -> it.getTripTime()).sum());
        return Optional.of(pilotByDate);
    }

    private final List<Long> getListPilotsIdByDate(LocalDate dat) {
        List<Long> listPilotId = reportRepository.findDistinctPilotIdByFlightDay(dat);
        List<Long> listCoPilotId = reportRepository.findDistinctCoPilotIdByFlightDay(dat);
        List<Long> lst = Stream.concat(
                        listPilotId.stream(), listCoPilotId.stream())
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        return lst;
    }

}