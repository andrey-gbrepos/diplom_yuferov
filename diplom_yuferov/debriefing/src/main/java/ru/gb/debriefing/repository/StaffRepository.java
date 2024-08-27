package ru.gb.debriefing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.debriefing.model.Staff;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findById(Long id);

    List<Staff> findAll();

    void deleteById(Long id);

}
