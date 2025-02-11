package com.devrezaur.main.controller;

import com.devrezaur.main.entity.Question;
import com.devrezaur.main.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QustionController {

    @Autowired
    private QuestionRepo qRepo;

    @PostMapping("/saveQuestions")
    public String saveQuestions(
            @RequestParam(value = "question", required = false) List<String> questions,
            @RequestParam(value = "opt1", required = false) List<String> opt1,
            @RequestParam(value = "opt2", required = false) List<String> opt2,
            @RequestParam(value = "opt3", required = false) List<String> opt3,
            @RequestParam(value = "opt4", required = false) List<String> opt4,
            @RequestParam(value = "correctAns", required = false) List<String> correctAns,
            Model model) {


        System.out.println("Received Questions: " + questions);
        System.out.println("Option A: " + opt1);
        System.out.println("Option B: " + opt2);
        System.out.println("Option C: " + opt3);
        System.out.println("Option D: " + opt4);
        System.out.println("Correct Answers: " + correctAns);


        if (questions == null || questions.isEmpty()) {
            System.out.println("ERROR: 'question' parameter is missing or empty!");
            model.addAttribute("message", "No questions received!");
            return "error";
        }


        List<Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question question = new Question();
            question.setDescription(questions.get(i));
            question.setOption1(opt1.get(i));
            question.setOption2(opt2.get(i));
            question.setOption3(opt3.get(i));
            question.setOption4(opt4.get(i));
            question.setCorrectAnswer(correctAns.get(i));

            questionList.add(question);
        }

        qRepo.saveAll(questionList);
        model.addAttribute("message", "Questions saved successfully!");

        return "savedQuestions";
    }

    @GetMapping("/viewQuestions")
    public String viewQuestions(Model model) {
        List<Question> questions = qRepo.findAll();
        model.addAttribute("questions", questions);
        return "viewQuestions";
    }
}
