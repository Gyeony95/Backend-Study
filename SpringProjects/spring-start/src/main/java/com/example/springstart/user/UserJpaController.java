package com.example.springstart.user;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import antlr.collections.List;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/users")
	public java.util.List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("id[%s] not found", id));
		}
		
		EntityModel<User> model = new EntityModel<>(user.get());
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()); //Hateoas를 사용해 하이퍼 미디어 링크 내려주기
		model.add(linkTo.withRel("all-users"));
		
		return model;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
		
		
		
	}
	
	@GetMapping("/users/{id}/posts")
	public java.util.List<Post> retrueveAllPostsByUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("id[%s] not found", id));
		}
		
		return user.get().getPosts();
	}
	
	
	@PostMapping("/users/{id}/post")
	public ResponseEntity<Post> createPost(@PathVariable int id,  @RequestBody Post post){
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("id[%s] not found", id));
		}
		
		post.setUser(user.get()); 
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedPost.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
		
		
		
	}
	
}
