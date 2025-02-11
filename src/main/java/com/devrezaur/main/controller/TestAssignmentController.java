package com.devrezaur.main.controller;


import com.devrezaur.main.entity.Invites;
import com.devrezaur.main.entity.Test;
import com.devrezaur.main.entity.User;
import com.devrezaur.main.repository.InviteRepo;
import com.devrezaur.main.repository.ResultRepo;
import com.devrezaur.main.repository.TestRepo;
import com.devrezaur.main.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TestAssignmentController {

    @Autowired
    private TestRepo testRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InviteRepo inviteRepo;


    @GetMapping("/inviteUser")
    public String showAssignUsersPage(Model model) {
        // Get all users with role "user"
        List<User> users = userRepo.findByRole("user");
        // Get all available tests
        List<Test> tests = testRepo.findAll();

        model.addAttribute("users", users);
        model.addAttribute("tests", tests);

        return "inviteUsers";  // Returns assignUsers.html
    }

    @PostMapping("/assignTest")
    public String assignTest(@RequestParam long testId, @RequestParam List<Integer> userIds) {
        Test test = testRepo.findById(testId).orElse(null);
        if (test != null) {


            System.out.println("chosen testId :" + testId);
            List<Long> userIdsLong = userIds.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());


            List<User> users = userRepo.findAllById(userIdsLong);

            for (User user : users) {

                inviteRepo.save(new Invites(test, user));

            }
        }

        return "successInvites";
    }

    @GetMapping("/exitPage")
    public String exitPage() {
        return "exitPage";
    }


}
