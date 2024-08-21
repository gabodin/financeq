package com.sb.financeq.services;

import com.sb.financeq.entities.User;
import com.sb.financeq.repositories.RoleRepository;
import com.sb.financeq.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
//        user.setPassword();
        user.addRole(roleRepository.findById(1).get());

        return userRepository.save(user);
    }
}
