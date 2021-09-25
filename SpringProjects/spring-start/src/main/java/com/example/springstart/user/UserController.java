package com.example.springstart.user;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.springstart.exception.CustomizeResponseEntityExceptionHandler;

@RestController
public class UserController {
	private UserDaoAService service;
	
	public UserController(UserDaoAService service) {
		this.service = service;
	}
	
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	
	@GetMapping(path = "/users/{id}")
	public User retrieveAllUser(@PathVariable int id){
		User user = service.fiundOne(id);
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		
		return user;
	}
	
	@DeleteMapping(path = "/users/{id}")
	public User deleteUser(@PathVariable int id){
		User user = service.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		
		return user;
	}
	
	@PostMapping(path= "/users")
	public ResponseEntity<User> createUser() {
		User savedUser = service.save(new User(null, "gh", new Date()));
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
	}
}
