package com.microlibrary.userservice.service;

import java.util.List;

import com.microlibrary.userservice.dto.UserDto;
import com.microlibrary.userservice.entity.Role;
import com.microlibrary.userservice.entity.User;

public interface IUserService {
    List<User> getAllUser();
	List<Role> getAllRole();
	User saveUser(User user);
	Role saveRole(Role role);
	void addRole(String username, String roleName);
	UserDto login(String username, String password);
	UserDto validateToken(String token);
}
