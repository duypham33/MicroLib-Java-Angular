package com.microlibrary.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microlibrary.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
