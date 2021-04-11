package com.example.demo.Controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.services.UserService;


@RequestMapping("users")
@RestController
public class UsersControllers {
	
	@Autowired
	UserService userService;
	@GetMapping
	public String getUser() {
		return "get user";
		
	}
	@PostMapping 
	public UserResponse creatUser(@RequestBody UserRequest userRequest) {

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest,userDto);
		UserDto creatUser = userService.createUser(userDto);
		
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(creatUser, userResponse);
		return userResponse;
	
	}
	@DeleteMapping
	public String deleteUser() {
		return "delete user ";

	}
	@PutMapping
	public  String updateUser() {
		return "update user";
	}
}
