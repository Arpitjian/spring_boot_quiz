package com.devrezaur.main.service;

import com.devrezaur.main.entity.Question;
import com.devrezaur.main.entity.Test;
import com.devrezaur.main.repository.QuestionRepo;
import com.devrezaur.main.repository.TestRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);
    private final TestRepo testRepository;
    private final QuestionRepo questionRepository;

    public TestService(TestRepo testRepository, QuestionRepo questionRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    public Test createTest(String testName, List<Long> questionIds, LocalDateTime startTime, LocalDateTime endTime) {
        Test test = new Test();
        test.setTestName(testName);
        test.setStartTime(startTime);
        test.setEndTime(endTime);

        // Fetch Questions
        List<Question> questions = questionRepository.findAllById(questionIds);
        logger.info("Fetched Questions: {}", questions);


        if (questions.isEmpty()) {
            logger.error("No questions found for IDs: {}", questionIds);
        }

        test.setQuestions(questions);


        Test savedTest = testRepository.save(test);
        logger.info("Saved Test ID: {}", savedTest.getId());
        logger.info("Test Questions: {}", savedTest.getQuestions());

        return savedTest;
    }
}

