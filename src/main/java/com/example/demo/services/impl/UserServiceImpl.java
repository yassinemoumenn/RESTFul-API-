package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

		UserEntity checkUser = userRepository.findByEmail(user.getEmail());
		if (checkUser != null)
			throw new RuntimeException("User  exixst");
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(util.generateStringId(30));
//		userRepository.save(userEntity); 
		UserEntity newUser = userRepository.save(userEntity);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(newUser, userDto);
		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {

		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {

		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		userEntity.setFirstname(userDto.getFirstname());
		userEntity.setLasttname(userDto.getLasttname());

		UserEntity userUpdated = userRepository.save(userEntity);

		UserDto user = new UserDto();

		BeanUtils.copyProperties(userUpdated, user);

		return user;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		userRepository.delete(userEntity);
	}


		@Override
		   public List<UserDto> getUsers(int page, int limit) {
		        if(page > 0) page = page - 1;

		   List<UserDto> usersResponse = new ArrayList<>();

		    Pageable pageableRequest = PageRequest.of(page, limit);
		    /*recupere tout in DB without pagination so we will add to user repository
		    *  another hirit place CrudRepository add PagingAndSortingRepository */ 
		    Page<UserEntity> userPage =  userRepository.findAll(pageableRequest);
		    List<UserEntity> users = userPage.getContent();

		    for(UserEntity userEntity: users) {

		        UserDto user =new UserDto();
		        BeanUtils.copyProperties(userEntity, user);
		        usersResponse.add(user);
		    }

		    return usersResponse;
		    }

	

//	@Override
//	public List<UserDto> getUsers(int page, int limit) {
//
//		List<UserDto> usersDto = new ArrayList<>();
//
//		Pageable pageableRequest = PageRequest.of(page, limit);
//		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
//		List<UserEntity> users = userPage.getContent();
//		return usersDto;
//	}

}
