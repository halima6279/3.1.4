package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class UserInit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin") == null) {
            Role userRole = roleRepository.getRoleByName("ROLE_USER");
            Role adminRole = roleRepository.getRoleByName("ROLE_ADMIN");


            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);


            User admin = new User();
            admin.setUsername("admin");
            admin.setSurname("admin");
            admin.setAge(26);
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));  // кодируем пароль
            admin.setRole(adminRoles);  // устанавливаем роли
            userRepository.save(admin);


            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);


            User user = new User();
            user.setUsername("user");
            user.setSurname("user");
            user.setAge(26);
            user.setEmail("user@gmail.com");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole(userRoles);
            userRepository.save(user);
        }
    }
}