package com.devrezaur.main.controller;

import com.devrezaur.main.entity.User;
//import com.devrezaur.main.service;
import com.devrezaur.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
//
   @Autowired
   private UserService userService;

    @PostMapping("/adminLogin")
    public String adminLogin(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("role") String role,
                             Model model) {

        // Check if user already exists
        User existingUser = userService.findByUserName(username).orElse(null);

        System.out.println("Received login request: " + username + " | " + role);

        if (existingUser == null) {
            // Create and save the new user
            User newUser = new User(username, password, role);
            userService.saveUser(newUser);
        } else {
            // Validate password and role
            if (!existingUser.getPassword().equals(password) || !existingUser.getRole().equalsIgnoreCase(role)) {
                model.addAttribute("errorMessage", "Invalid credentials!");
                return "loginPage";  // Return to login if invalid
            }
        }
        // Redirect based on role
        if ("admin".equalsIgnoreCase(role)) {
            return "redirect:/adminDashboard";
        } else {
            return "redirect:/userDashboard";
        }
    }

    @PostMapping("/userPage")
    public String userLogin(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("role") String role,
                             Model model) {

        // Check if user already exists
        User existingUser = userService.findByUserName(username).orElse(null);

        System.out.println("Received login request: " + username + " | " + role);

        if (existingUser == null) {
            // Create and save the new user
            User newUser = new User(username, password, role);
            userService.saveUser(newUser);
        } else {
            // Validate password and role
            if (!existingUser.getPassword().equals(password) || !existingUser.getRole().equalsIgnoreCase(role)) {
                model.addAttribute("errorMessage", "Invalid credentials!");
                return "loginPage";  // Return to login if invalid
            }
        }
        // Redirect based on role
        if ("admin".equalsIgnoreCase(role)) {
            return "redirect:/adminDashboard";
        } else {
            System.out.println("Insdie the redirect page");
            return "userDashoard";
        }
    }

@GetMapping("userDashboard")
public String userDashboard(){
        return "userDashoard";
}
    @GetMapping("/login")
    public String home() {
        return "loginPage"; // Ensure loginPage.html exists in templates/
    }
}
