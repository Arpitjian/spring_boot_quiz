package com.devrezaur.main.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String role;

    public String getPassword() {
        return password;
    }
    public User() {
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public User(String username, String password, String userRole) {
        this.userName = username;
        this.password = password;
        this.role = userRole;
    }

    @Column(nullable = false)
    private  String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

