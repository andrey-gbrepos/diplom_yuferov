package ru.gb.debriefing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.debriefing.model.*;
import ru.gb.debriefing.repository.*;
import ru.gb.debriefing.security.Role;
import ru.gb.debriefing.security.User;
import ru.gb.debriefing.security.UserRole;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


@SpringBootApplication
public class DebriefingApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DebriefingApplication.class, args);

        UserRepository userRepository = ctx.getBean(UserRepository.class);
        User admLogin = new User();admLogin.setLogin("admin");
        admLogin.setPassword("$2a$12$o8F7Cgghy8/8WOalVtg/s.OzMRNm7BdSRUbZNWsjMk/pO094b/zBq");
        User usLogin1 = new User();usLogin1.setLogin("us1pilot");
        usLogin1.setPassword("$2a$12$7p.FEgP8CDrS9zWS72dgg.Gbo7Sez51C8eu4EAPjnc5J4LIGjymyq");
        User usLogin2 = new User();usLogin2.setLogin("us2pilot");
        usLogin2.setPassword("$2a$12$YEkp2Fex1tG8yX8/89b3x.aFW5ZSBPfsyJ0OHviUVauF4PI4guXY6");
        User usLogin3 = new User();usLogin3.setLogin("us3pilot");
        usLogin3.setPassword("$2a$12$NG7oYL.dEsZf.tl2.ZFeju3YrnwltEYlb5JL.VPIqSSU9x8BGLyXK");
        User usLogin4 = new User();usLogin4.setLogin("us4pilot");
        usLogin4.setPassword("$2a$12$qG21e2rdsY6EZSzsevDvG.CU9682WwQk.gI9KH7EA5dj5jZckrDRy");
        User usLogin5 = new User();usLogin5.setLogin("us5pilot");
        usLogin5.setPassword("$2a$12$dbLeJPbXwRlAwc73Ek8YAOLy8v8gMvH3p65RJCrJBohqiYbVOF2Pq");
        User usLogin6 = new User();usLogin6.setLogin("engineer");
        usLogin6.setPassword("$2a$12$wXys1nX9vnAYlUu2A0D2V.fNTaOkrpvey8duLIsoC1ZN8TH4AqZWq");
        User usLogin7 = new User();usLogin7.setLogin("logist");
        usLogin7.setPassword("$2a$12$xzSrTICc8K2Q97jtlfAm9ecmTcpBXc4TPH9EafDm40dR5gEt15NKG");
        User usLogin8 = new User();usLogin8.setLogin("qwerty");
        usLogin8.setPassword("$2a$12$bKlw18/71ajq0cZbgWf/g.X4t3vfq2fgLPy.dfzuPmqEdF3T5crr.");
        admLogin = userRepository.save(admLogin);usLogin1 = userRepository.save(usLogin1);
        usLogin2 =userRepository.save(usLogin2);usLogin3 =userRepository.save(usLogin3);
        usLogin4 =userRepository.save(usLogin4);usLogin5 =userRepository.save(usLogin5);
        usLogin6 =userRepository.save(usLogin6);usLogin7 =userRepository.save(usLogin7);
        usLogin8 =userRepository.save(usLogin8);

        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
        Role admRole = new Role();
        admRole.setRole("admin");
        Role pilotRole = new Role();
        pilotRole.setRole("pilot");
        Role logistRole = new Role();
        logistRole.setRole("doclogist");
        Role commandRole = new Role();
        commandRole.setRole("commandor");
        Role enginRole = new Role();
        enginRole.setRole("engineer");
        admRole = roleRepository.save(admRole);
        pilotRole = roleRepository.save(pilotRole);
        logistRole = roleRepository.save(logistRole);
        commandRole = roleRepository.save(commandRole);
        enginRole = roleRepository.save(enginRole);

        UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
        UserRole userRole1 = new UserRole();
        userRole1.setUserId(userRepository.findByLogin("admin").get().getId());
        userRole1.setRoleId(roleRepository.findByRole("admin").get().getId());
        userRole1 = userRoleRepository.save(userRole1);

        UserRole userRole2 = new UserRole();
        userRole2.setUserId(userRepository.findByLogin("us1pilot").get().getId());
        userRole2.setRoleId(roleRepository.findByRole("admin").get().getId());
        userRole2 = userRoleRepository.save(userRole2);

        UserRole userRole3 = new UserRole();
        userRole3.setUserId(userRepository.findByLogin("us1pilot").get().getId());
        userRole3.setRoleId(roleRepository.findByRole("commandor").get().getId());
        userRole3 = userRoleRepository.save(userRole3);

        UserRole userRole4 = new UserRole();
        userRole4.setUserId(userRepository.findByLogin("us2pilot").get().getId());
        userRole4.setRoleId(roleRepository.findByRole("pilot").get().getId());
        userRole4 = userRoleRepository.save(userRole4);

        UserRole userRole5 = new UserRole();
        userRole5.setUserId(userRepository.findByLogin("us3pilot").get().getId());
        userRole5.setRoleId(roleRepository.findByRole("pilot").get().getId());
        userRole5 = userRoleRepository.save(userRole5);

        UserRole userRole6 = new UserRole();
        userRole6.setUserId(userRepository.findByLogin("us4pilot").get().getId());
        userRole6.setRoleId(roleRepository.findByRole("pilot").get().getId());
        userRole6 = userRoleRepository.save(userRole6);

        UserRole userRole7 = new UserRole();
        userRole7.setUserId(userRepository.findByLogin("us5pilot").get().getId());
        userRole7.setRoleId(roleRepository.findByRole("pilot").get().getId());
        userRole7 = userRoleRepository.save(userRole7);

        UserRole userRole8 = new UserRole();
        userRole8.setUserId(userRepository.findByLogin("logist").get().getId());
        userRole8.setRoleId(roleRepository.findByRole("doclogist").get().getId());
        userRole8 = userRoleRepository.save(userRole8);

        UserRole userRole9 = new UserRole();
        userRole9.setUserId(userRepository.findByLogin("us5pilot").get().getId());
        userRole9.setRoleId(roleRepository.findByRole("doclogist").get().getId());
        userRole9 = userRoleRepository.save(userRole9);

        UserRole userRole10 = new UserRole();
        userRole10.setUserId(userRepository.findByLogin("engineer").get().getId());
        userRole10.setRoleId(roleRepository.findByRole("engineer").get().getId());
        userRole10 = userRoleRepository.save(userRole10);

        UserRole userRole11 = new UserRole();
        userRole11.setUserId(userRepository.findByLogin("us1pilot").get().getId());
        userRole11.setRoleId(roleRepository.findByRole("pilot").get().getId());
        userRole11 = userRoleRepository.save(userRole11);

        StaffRepository staffRepository = ctx.getBean(StaffRepository.class);
        for (int i = 1; i < 12; i++) {
            Staff person = new Staff();
            LocalDate startExp = LocalDate.now();
            person.setName("instructor_" + i);
            person.setPositionId(ThreadLocalRandom.current().nextLong(1, 6));
            person.setExperience(startExp.minusYears(ThreadLocalRandom.current().nextLong(1, 8)));
            staffRepository.save(person);
        }

        VarStaffRepository varStaffRepository = ctx.getBean(VarStaffRepository.class);
        for (int i = 1; i < 27; i++) {
            VarStaff student = new VarStaff();
            Long days = ThreadLocalRandom.current().nextLong(2, 35);
            LocalDate startPract = LocalDate.now().minusDays(days);
            student.setName("student_" + i);
            student.setStartPractice(startPract.minusDays(days));
            if (days > 30) student.setFinishPractice(LocalDate.now());
            student.setFlightTime(ThreadLocalRandom.current().nextLong(20, 1600));
            varStaffRepository.save(student);
        }

        PlaneRepository planeRepository = ctx.getBean(PlaneRepository.class);
        for (int i = 1; i < 6; i++) {
            Planes plane = new Planes();
            plane.setPlaneNumber("RA-017" + 16 * i);
            plane.setCountLand(ThreadLocalRandom.current().nextLong(1200, 2000));
            plane.setEngineTime((double) Math.round(ThreadLocalRandom.current()
                    .nextDouble(1200, 2000) * 10) / 10);
            planeRepository.save(plane);
        }

        ReportRepository reportRepository = ctx.getBean(ReportRepository.class);
        for (int i = 0; i < 100; i++) {
            Reports report = new Reports();
            Long days = ThreadLocalRandom.current().nextLong(0, 32);
            report.setFlightDay(LocalDate.now().minusDays(days));
            report.setPilotId(ThreadLocalRandom.current().nextLong(1, 11));
            report.setStudentId(ThreadLocalRandom.current().nextLong(1, 27));
            if (report.getStudentId() / 3 == 0) report.setCoPilotId(ThreadLocalRandom.current().nextLong(1, 11));
            report.setPlaneId(ThreadLocalRandom.current().nextLong(1, 6));
            report.setTripCount(ThreadLocalRandom.current().nextLong(1, 19));
            report.setTripTime(ThreadLocalRandom.current().nextLong(21, 190));
            long tempTime = report.getTripTime() / 7;
            report.setEngineTime((double) tempTime / 10);
            report.setFuelConsumpt(report.getTripTime() * 3 / 10);
            reportRepository.save(report);
        }

        PositionRepository positonRepository = ctx.getBean(PositionRepository.class);
        String[] positions = {"пилот-инструктор", "ст. пилот-инструктор", "зам. командира АЭ",
                "командир АЭ", "зам. командира АО", "командир АО",
                "ст. пилот-инструктор ЛМО", "нач. ЛМО"};
        for (int i = 0; i < positions.length; i++) {
            Positions position = new Positions();
            position.setPosition(positions[i]);
            positonRepository.save(position);
        }
    }
}
