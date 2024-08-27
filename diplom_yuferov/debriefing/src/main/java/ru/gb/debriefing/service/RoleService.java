package ru.gb.debriefing.service;

import org.springframework.stereotype.Service;
import ru.gb.debriefing.repository.RoleRepository;
import ru.gb.debriefing.security.Role;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }
    public Optional<Role> create(Role role) {
        return Optional.of(roleRepository.save(role));
    }
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    public Optional<Role> update(Role role) {
        Optional<Role> rol = roleRepository.findById(role.getId());
        if (rol.isPresent()) rol.get().setRole(role.getRole());
        return rol;
    }
}
