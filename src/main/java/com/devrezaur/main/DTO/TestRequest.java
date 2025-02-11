package com.devrezaur.main.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class TestRequest {
    private String testName;
    private List<Long> questionIds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public TestRequest() {
    }

    public TestRequest(String testName, List<Long> questionIds, LocalDateTime startTime, LocalDateTime endTime) {
        this.testName = testName;
        this.questionIds = questionIds;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
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


    @Override
    public String toString() {
        return "TestRequest{testName='" + testName + "', startTime=" + startTime + ", endTime=" + endTime + "}";
    }
}

