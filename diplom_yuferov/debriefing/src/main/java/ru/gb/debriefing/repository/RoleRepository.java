package ru.gb.debriefing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.debriefing.security.Role;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByRole(String name);
}
