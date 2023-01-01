package com.microlibrary.userservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microlibrary.userservice.dto.UserDto;
import com.microlibrary.userservice.entity.Role;
import com.microlibrary.userservice.entity.User;
import com.microlibrary.userservice.repository.RoleRepository;
import com.microlibrary.userservice.repository.UserRepository;

@Service @Transactional
public class UserService implements IUserService, UserDetailsService {
    @Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User is not found!");
		}else {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			user.getRoles().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			});

			return new org.springframework.security.core.userdetails.User(user.getUsername(), 
            user.getPassword(), authorities);
		}
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		
		return roleRepository.save(role);
	}

	@Override
	public void addRole(String username, String roleName) {
		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	public List<Role> getAllRole() {
		
		return roleRepository.findAll();
	}

	@Override
	public UserDto login(String username, String password) {
		User user = userRepository.findByUsername(username);
		UserDto dto = new UserDto();
		if(user !=null) {
			BeanUtils.copyProperties(user, dto);
			if(passwordEncoder.matches(password, dto.getPassword())) {
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				String access_token = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 1000)))
						.sign(algorithm);
				String refreshtoken = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 1000)))
						.sign(algorithm);
				dto.setToken(access_token);
				dto.setRefreshtoken(refreshtoken);
			}
		}

		return dto;
	}

	@Override
	public UserDto validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		String username = decodedJWT.getSubject();
		
		User user = userRepository.findByUsername(username);
		UserDto dto = new UserDto();
		if(user !=null) {
			BeanUtils.copyProperties(user, dto);
			dto.setToken(token);
		}
        
		return dto;
	}
}
