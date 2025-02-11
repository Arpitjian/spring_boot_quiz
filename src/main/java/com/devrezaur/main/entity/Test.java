package com.devrezaur.main.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String testName;

    @ManyToMany
    @JoinTable(
            name = "test_questions",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"test_id", "question_id"})
    )

    private List<Question> questions;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    // Constructors
    public Test() {
    }

    public Test(String testName, List<Question> questions, LocalDateTime startTime, LocalDateTime endTime) {
        this.testName = testName;
        this.questions = questions;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Test(String testName, LocalDateTime startTime, LocalDateTime endTime) {
        this.testName = testName;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}

