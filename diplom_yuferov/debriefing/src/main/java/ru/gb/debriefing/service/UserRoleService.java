package ru.gb.debriefing.service;

import org.springframework.stereotype.Service;
import ru.gb.debriefing.repository.RoleRepository;
import ru.gb.debriefing.repository.UserRepository;
import ru.gb.debriefing.repository.UserRoleRepository;
import ru.gb.debriefing.responsedto.UsRolName;
import ru.gb.debriefing.security.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository,
                           UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UsRolName> findAll() {
        List<UserRole> listUsRol = userRoleRepository.findAll();
        List<UsRolName> listUsRolName = new ArrayList<>();
        for (UserRole item : listUsRol) {
            UsRolName usRolName = new UsRolName();
            usRolName.setUserId(item.getUserId());
            usRolName.setUserName(userRepository.findById(item.getUserId()).get().getLogin());
            usRolName.setRoleId(item.getRoleId());
            usRolName.setRoleName(roleRepository.findById(item.getRoleId()).get().getRole());
            listUsRolName.add(usRolName);
        }
        return listUsRolName;
    }

    public Optional<UserRole> findById(Long id) {
        return userRoleRepository.findById(id);
    }

    public Optional<UserRole> create(UserRole userRole) {
        return Optional.of(userRoleRepository.save(userRole));
    }

    public void delete(Long id) {
        userRoleRepository.deleteById(id);
    }

    public Optional<UserRole> update(UserRole userRole) {
        Optional<UserRole> usRol = userRoleRepository.findById(userRole.getId());
        if (usRol.isPresent()) {
            usRol.get().setUserId(userRole.getUserId());
            usRol.get().setRoleId(userRole.getRoleId());
        }
        return usRol;
    }
}
