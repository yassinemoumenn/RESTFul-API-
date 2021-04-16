package com.example.demo.services;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.dto.UserDto;
import com.example.demo.response.UserResponse;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);
    UserDto	getUser (String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String id, UserDto userDto);
	void deleteUser(String userId);
	List<UserDto> getUsers(int page, int limit);


}
