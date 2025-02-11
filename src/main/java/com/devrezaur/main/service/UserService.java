package com.devrezaur.main.service;

import com.devrezaur.main.entity.User;
import com.devrezaur.main.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // ✅ Find user by userName
    public Optional<User> findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    // ✅ Find users by role (e.g., "USER" or "ADMIN")
    public List<User> findByRole(String role) {
        return userRepo.findByRole(role);
    }

    // ✅ Find user by ID
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    // ✅ Save user
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    // ✅ Get all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
