package com.microlibrary.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microlibrary.userservice.dto.AddRole;
import com.microlibrary.userservice.dto.UserDto;
import com.microlibrary.userservice.entity.Role;
import com.microlibrary.userservice.entity.User;
import com.microlibrary.userservice.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
	private IUserService userService;

	
	@GetMapping("/listUser")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
    
	@GetMapping("/listRole")
	public List<Role> getAllRole(){
		return userService.getAllRole();
	}

	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@PostMapping("/addRoleToUser")
	public void addRoleToUser(@RequestBody AddRole model) {
		 userService.addRole(model.getUsername(), model.getRolename());
	}

	@PostMapping("/addRole")
	public Role addRole(@RequestBody Role role) {
		return userService.saveRole(role);
	}

	@PostMapping("/login")
	public UserDto login(@RequestBody UserDto dto) {
		return userService.login(dto.getUsername(), dto.getPassword());
	}

	@PostMapping("/validateToken")
	public UserDto validateToken(@RequestBody UserDto dto) {
		return userService.validateToken(dto.getToken());
	}
	
	@GetMapping
	public String homePage() {
		return "Homepage";
	}
}
