package com.devrezaur.main.controller;

import com.devrezaur.main.entity.User;
import com.devrezaur.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {


    @GetMapping("/adminDashboard")
    public String adminLogin() {

        return "adminDashboard";
    }

    @PostMapping("/uploadQuestion")
    public String uploadQuestion() {

        return "uploadQuestions";
    }

}
