package com.example.demo.services;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);
    UserDto	getUser (String email);
}
