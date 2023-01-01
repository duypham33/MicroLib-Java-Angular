package com.microlibrary.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microlibrary.userservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
