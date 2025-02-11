package com.devrezaur.main.repository;

import com.devrezaur.main.entity.Question;
import com.devrezaur.main.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {
    Optional<Test> findByTestName(String testName);

    @Repository
    public interface QuestionRepo extends JpaRepository<Question, Long> {
        @Query("SELECT q FROM Question q JOIN q.tests t WHERE t.id = :testId")
        List<Question> findQuestionsById(@Param("testId") Long testId);
    }


}
