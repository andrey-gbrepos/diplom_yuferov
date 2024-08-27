package ru.gb.debriefing.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.debriefing.repository.RoleRepository;
import ru.gb.debriefing.repository.UserRepository;
import ru.gb.debriefing.repository.UserRoleRepository;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        List<Long> roleId = userRoleRepository.findRoleIdByUserId(user.getId());
        List<String> userRoles = roleId.stream().map(it -> roleRepository.findById(it).get().getRole()).toList();

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                userRoles.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}
