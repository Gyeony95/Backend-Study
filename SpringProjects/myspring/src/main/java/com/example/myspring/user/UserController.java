package com.example.myspring.user;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	//모든 유저를 불러오는 api
	@GetMapping(path = "/users")
	public List<User> getAllUsers(){
		return userService.getAllUser();
	}
	
	//한명의 유저 정보를 불러오는 api 
	@GetMapping(path = "/user/{id}")
	public User getUser(@PathVariable String id) {
		return userService.getUser(id);
	}
	
	
	//유저 등록 api
	@PostMapping(path = "/user/{id}/{pw}")
	public void addUser(@PathVariable String id, @PathVariable String pw) {
		userService.addUser(id, pw);
	}
	
	//유저 정보 수정 api
	@PutMapping(path = "/user/{id}/{pw}")
	public void updateUser(@PathVariable String id, @PathVariable String pw) {
		userService.updateUser(id, pw);
	}
	
	//유저 정보 삭제 api
	@DeleteMapping(path = "/user/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
	
}
