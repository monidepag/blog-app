package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.exceptions.*;
import com.blog.entities.*;
import com.blog.payloads.UserDTO;
import com.blog.repositories.*;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDTO createUser(UserDTO userDto) {

		User user = this.dtoToUser(userDto);

		User savedUser = this.userRepo.save(user);

		return this.UserToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		User updatedUser = this.userRepo.save(user);
		UserDTO userDto1 = this.UserToDto(updatedUser);

		return userDto1;
	}

	@Override
	public UserDTO getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.UserToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDtos = users.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		this.userRepo.delete(user);

	}

	public User dtoToUser(UserDTO userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		return user;
	}

	public UserDTO UserToDto(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		return userDto;
	}

}
