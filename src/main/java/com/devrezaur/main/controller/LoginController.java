package com.devrezaur.main.controller;


import com.devrezaur.main.entity.User;

import com.devrezaur.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/adminLogin")
    public String adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") String role, Model model) {


        User existingUser = userService.findByUserName(username).orElse(null);

        System.out.println("Received login request: " + username + " | " + role);

        if (existingUser == null) {

            User newUser = new User(username, password, role);
            userService.saveUser(newUser);
        } else {

            if (!existingUser.getPassword().equals(password) || !existingUser.getRole().equalsIgnoreCase(role)) {
                model.addAttribute("errorMessage", "Invalid credentials!");
                return "loginPage";  // Return to login if invalid
            }
        }

        if ("admin".equalsIgnoreCase(role)) {
            return "redirect:/adminDashboard";
        } else {
            return "redirect:/userDashboard";
        }
    }

    @PostMapping("/userPage")
    public String userLogin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") String role, Model model) {


        User existingUser = userService.findByUserName(username).orElse(null);

        System.out.println("Received login request: " + username + " | " + role);

        if (existingUser == null) {

            User newUser = new User(username, password, role);
            userService.saveUser(newUser);
        } else {

            if (!existingUser.getPassword().equals(password) || !existingUser.getRole().equalsIgnoreCase(role)) {
                model.addAttribute("errorMessage", "Invalid credentials!");
                return "loginPage";  // Return to login if invalid
            }
        }

        if ("admin".equalsIgnoreCase(role)) {
            return "redirect:/adminDashboard";
        } else {
            System.out.println("Insdie the redirect page");
            return "userDashoard";
        }
    }

    @GetMapping("userDashboard")
    public String userDashboard() {
        return "userDashoard";
    }

    @GetMapping("/login")
    public String home() {
        return "loginPage";
    }
}
