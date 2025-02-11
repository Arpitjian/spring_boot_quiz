package com.devrezaur.main.repository;

import com.devrezaur.main.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResultRepo extends JpaRepository<Result, Long> {
    List<Result> findByUser_Id(Long userId);


}
