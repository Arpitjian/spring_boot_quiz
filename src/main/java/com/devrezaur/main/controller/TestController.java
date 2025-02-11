package com.devrezaur.main.controller;

import com.devrezaur.main.DTO.TestRequest;
import com.devrezaur.main.entity.*;
import com.devrezaur.main.repository.*;
import com.devrezaur.main.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private TestRepo testRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private InviteRepo invite;

    @Autowired
    private UserResponseRepo userResponseRepo;

    @Autowired
    private  ResultRepo rRepo;

    @PostMapping("/saveTest")
    public String saveTest(
           @RequestParam String testName,
           @RequestParam String startTime,
           @RequestParam String endTime,
           @RequestParam String selectedQuestionIds) {

       System.out.println("Received Test Name: " + testName);
       System.out.println("Received Start Time: " + startTime);
       System.out.println("Received End Time: " + endTime);
       System.out.println("Received Question IDs: " + selectedQuestionIds);

       // Parse start and end times
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
       LocalDateTime parsedStartTime = LocalDateTime.parse(startTime, formatter);
       LocalDateTime parsedEndTime = LocalDateTime.parse(endTime, formatter);

       // Fetch selected questions from DB
       List<Question> selectedQuestions = new ArrayList<>();

       if (!selectedQuestionIds.isEmpty()) {
           List<Long> questionIds = List.of(selectedQuestionIds.split(",")).stream()
                   .map(Long::parseLong)
                   .collect(Collectors.toList());

           selectedQuestions.addAll(questionRepo.findAllById(questionIds));
       }

       // Create and save test
       Test test = new Test();
       test.setTestName(testName);
       test.setStartTime(parsedStartTime);
       test.setEndTime(parsedEndTime);
       test.setQuestions(selectedQuestions);

       testRepo.save(test);

       return "success";
   }
    @GetMapping("/createTest")
    public String createTest(Model model) {
        List<Question> questions = questionRepo.findAll();  // Fetch all available questions
        model.addAttribute("questions", questions);
        return "createTest";  // Ensure createTest.html exists in src/main/resources/templates/
    }

    @PostMapping("/validateDetails")
    public String validateTest(@RequestParam String username, @RequestParam String testname, Model model) {
        System.out.println("Inside InviteController - validateTest()");

        // Find User by username
        Optional<User> userOpt = userRepo.findByUserName(username);
        if (!userOpt.isPresent()) {
            System.out.println("User not found: " + username);
            model.addAttribute("error", "User not found.");
            return "index";
        }
        User user = userOpt.get();
        System.out.println("User found: " + user.getUserName());

        // Find Test by testName
        Optional<Test> testOpt = testRepo.findByTestName(testname);
        if (!testOpt.isPresent()) {
            System.out.println("Test not found: " + testname);
            model.addAttribute("error", "Test not found.");
            return "index";
        }
        Test test = testOpt.get();
        System.out.println("Test found: " + test.getTestName());

        // Check if user is invited
        boolean isInvited = invite.existsByTestAndUser(test, user);
        System.out.println("User invited status: " + isInvited);

        if (isInvited) {
            System.out.println("Redirecting to /testPage with testId: " + test.getId() + " and userId: " + user.getId());
            return "redirect:/testPage?testId=" + test.getId() + "&userId=" + user.getId();
        } else {
            System.out.println("User is not invited.");
            model.addAttribute("error", "You are not invited to this test.");
            return "index";
        }
    }

    @GetMapping("/testDetails")
    public  String validateTestDetails(){
        return  "testDetails";
    }

    @GetMapping("/testPage")
    public String showTestPage(@RequestParam Long testId, @RequestParam Integer userId, Model model) {
        System.out.println("inside the Test COntroller");
        Test test = testRepo.findById(testId).orElse(null);

        if (test == null) {
            return "error"; // Handle error
        }

        List<Question> questions = questionRepo.findQuestionsById(testId);

        model.addAttribute("testId", testId);
        model.addAttribute("userId", userId);
        model.addAttribute("testName", test.getTestName());
        model.addAttribute("questions", questions);
        for (Question q : questions) {
            System.out.println("Question: " + q.getDescription());
            System.out.println("Options: " + q.getOption1() + ", " + q.getOption2() + ", " + q.getOption3() + ", " + q.getOption4());
        }


        return "testPage";
    }
    @PostMapping("/saveUserResponse")
    public String saveUserResponse(@RequestParam String testId,
                                   @RequestParam String userId,
                                   HttpServletRequest request,
                                   Model model) {
        System.out.println("Received testId: " + testId);
        System.out.println("Received userId: " + userId);
        System.out.println("Saving user response...");


        // Fetch user and test from DB
        System.out.println("Before testid");
        Long testIdLong = Long.parseLong(testId);
        System.out.println("Before userid");
        Long userIdLong = Long.parseLong(userId);

        // Fetch user and test from DB
        Optional<User> userOpt = userRepo.findById(userIdLong);
        Optional<Test> testOpt = testRepo.findById(testIdLong);

        if (!userOpt.isPresent() || !testOpt.isPresent()) {
            return "index";
        }


        if (!userOpt.isPresent() || !testOpt.isPresent()) {
            return "error";  // Redirect to an error page if data is missing
        }

        User user = userOpt.get();
        Test test = testOpt.get();

        int correctAnswers = 0;
        int totalQuestions = 0;

        // Process user responses
        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("answer-")) {
                Long questionId = Long.parseLong(paramName.replaceAll("\\D+", ""));
                String selectedAnswer = request.getParameter(paramName);

                Optional<Question> questionOpt = questionRepo.findById(questionId);
                if (questionOpt.isPresent()) {
                    Question question = questionOpt.get();
                    totalQuestions++;

                    // Check correctness
                    if (selectedAnswer.equals(question.getCorrectAnswer())) {
                        correctAnswers++;
                    }

                    // Save response
                    Response response = new Response(test, question, user, selectedAnswer);
                    userResponseRepo.save(response);
                }
            }
        }

        // Save test result
        Result result = new Result(test, user, correctAnswers, totalQuestions);
        result.setCorrectAnswers(correctAnswers);
        System.out.println("test : "+test);
        System.out.println("user : "+user);
        System.out.println("totalQuestions : "+totalQuestions);
        System.out.println("correctAnswers : "+correctAnswers);

       // correctAnswers
        rRepo.save(result);


        model.addAttribute("test", test);
        System.out.println("Test object: " + test);
        System.out.println("Test object: " + test.getTestName());


        model.addAttribute("user", user);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("scorePercentage", result.getScorePercentage());

        //rRepo.save(result);


        return "viewResult";  // Return the result page
    }

}
