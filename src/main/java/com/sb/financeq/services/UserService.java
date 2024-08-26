package com.sb.financeq.services;

import com.sb.financeq.entities.User;
import com.sb.financeq.repositories.RoleRepository;
import com.sb.financeq.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        System.out.println(user);
        user.setActive(true);
        user.setRegistrationDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(roleRepository.findById(1).get());

        return userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();

            return userRepository.findByEmail(username).orElseThrow(() ->
                    new UsernameNotFoundException("Could not find user with username: " + username));
        }
        return null;
    }
}
