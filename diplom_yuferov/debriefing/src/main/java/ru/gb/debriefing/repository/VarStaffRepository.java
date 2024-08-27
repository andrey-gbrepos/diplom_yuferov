package ru.gb.debriefing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.debriefing.model.VarStaff;

import java.util.List;
import java.util.Optional;

@Repository
public interface VarStaffRepository extends JpaRepository<VarStaff, Long> {

    Optional<VarStaff> findById(Long id);

    List<VarStaff> findAll();

    void deleteById(Long id);
}
