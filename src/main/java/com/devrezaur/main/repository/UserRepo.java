package com.devrezaur.main.repository;

import com.devrezaur.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
   Optional<User> findByUserName(String username);  // Find user by username

  //  List<User> findByUserRole(String role);
   //Optional<User> findById(Long id);

    List<User> findByRole(String user);



  //  Optional<User> findByUsername(String username);  // Find user by username

   // List<User> findByUserRole(String role);
    //List<User> findById(Long userId);
}

