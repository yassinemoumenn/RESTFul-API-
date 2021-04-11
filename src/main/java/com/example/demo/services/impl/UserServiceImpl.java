package com.example.demo.services.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.utils.Utils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
      Utils util;
	@Override
	public UserDto createUser(UserDto user) {
		
		UserEntity checkUser= userRepository.findByEmail(user.getEmail());
		if(checkUser != null) throw new RuntimeException("User  exixst");		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(util.generateStringId(30));
//		userRepository.save(userEntity); 
		UserEntity newUser = userRepository.save(userEntity);
		UserDto userDto= new UserDto(); 
		BeanUtils.copyProperties(newUser, userDto);
		return userDto;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity =  userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
	}
	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity =  userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);

      UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	} 

	
}
