package com.example.springstart.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	//kkkkkkkk naneun sookong ida!
	
	@GetMapping(path = "/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		User user = service.fiundOne(id);
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		EntityModel<User> model = new EntityModel<>(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()); //Hateoas를 사용해 하이퍼 미디어 링크 내려주기
		model.add(linkTo.withRel("all-users"));
		
		
		
		return model;
	}
	
    // 전체 사용자 목록
    @GetMapping("/users2")
    public ResponseEntity<CollectionModel<EntityModel<User>>> retrieveUserList2() {
        List<EntityModel<User>> result = new ArrayList<>();
        List<User> users = service.findAll();

        for (User user : users) {
            EntityModel entityModel = EntityModel.of(user);
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()).withSelfRel());

            result.add(entityModel);
        }

        return ResponseEntity.ok(CollectionModel.of(result, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()).withSelfRel()));
    }
	
	@DeleteMapping(path = "/users/{id}")
	public User deleteUser(@PathVariable int id){
		User user = service.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		
		return user;
	}
	
	//@Valid -> validation check 
	@PostMapping(path= "/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
	}
}
