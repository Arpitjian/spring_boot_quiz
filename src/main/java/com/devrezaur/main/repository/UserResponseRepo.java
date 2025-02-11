package com.devrezaur.main.repository;

import com.devrezaur.main.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResponseRepo extends JpaRepository<Response, Integer> {
}
