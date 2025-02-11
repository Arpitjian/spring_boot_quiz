package com.devrezaur.main.entity;



import java.util.List;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "user_responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer responseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;  // Reference to the Test entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;  // Reference to the Question entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Reference to the User entity

    @Column(name = "answer_chosen", nullable = false)
    private String answerChosen;  // Answer selected by the user

    // Constructors
    public Response() {}

    public Response(Test test, Question question, User user, String answerChosen) {
        this.test = test;
        this.question = question;
        this.user = user;
        this.answerChosen = answerChosen;
    }

    // Getters and Setters
    public Integer getResponseId() {
        return responseId;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAnswerChosen() {
        return answerChosen;
    }

    public void setAnswerChosen(String answerChosen) {
        this.answerChosen = answerChosen;
    }
}
