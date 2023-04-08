package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDTO;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//POST - create user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto)
	{
		UserDTO createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//PUT - update user
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto, @PathVariable("userId") Integer uid)
	{
		UserDTO updatedUser = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUser);
	} 
	
	//DELETE - delete user
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	{
		this.deleteUser(uid);
		return new ResponseEntity(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}
	
	//GET - user get
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//GET - user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

}
