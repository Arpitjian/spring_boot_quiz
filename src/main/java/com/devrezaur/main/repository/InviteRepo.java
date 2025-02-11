package com.devrezaur.main.repository;

import com.devrezaur.main.entity.Invites;
import com.devrezaur.main.entity.Test;
import com.devrezaur.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InviteRepo extends JpaRepository<Invites, Integer> {

    List<Invites> findByTest(Test test);

    @Query("SELECT COUNT(i) > 0 FROM Invites i WHERE i.test = :test AND i.user = :user")
    boolean existsByTestAndUser(@Param("test") Test test, @Param("user") User user);
}
