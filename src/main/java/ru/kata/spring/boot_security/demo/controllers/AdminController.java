package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.List;

@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public String findAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("users", userList);
        return "all_users";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm() {
        return "create_user";
    }

    @PostMapping("/user-create")
    public String createUser(User user,
                             @RequestParam(value = "rolesId") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        List<Role> listRoles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "update_user";
    }

    @PostMapping("/user-update")
    public String updateUser(User user,
                             @RequestParam(value = "rolesId") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
}
