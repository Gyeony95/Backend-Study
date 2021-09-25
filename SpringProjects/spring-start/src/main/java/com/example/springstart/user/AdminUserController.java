package com.example.springstart.user;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.springstart.exception.CustomizeResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/admin") //이거 선언해주면 url 앞에 공통적으로 붙어서 호출되게됨
public class AdminUserController {
	private UserDaoAService service;
	
	public AdminUserController(UserDaoAService service) {
		this.service = service;
	}
	
	@GetMapping(path = "/users")
	public MappingJacksonValue retrieveAllUsers(){
		List<User> users = service.findAll();
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter //데이터 필터링
				.filterOutAllExcept("id","name", "joinDate","password");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);// 필터를 사용할수 있는 값으로 바꿔줌
		
		MappingJacksonValue mapping = new MappingJacksonValue(users);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	
	@GetMapping(path = "/users/{id}")
	public MappingJacksonValue retrieveUser(@PathVariable int id){
		User user = service.fiundOne(id);
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter //데이터 필터링
				.filterOutAllExcept("id","name", "password","ssn");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);// 필터를 사용할수 있는 값으로 바꿔줌
		
		MappingJacksonValue mapping = new MappingJacksonValue(user);
		mapping.setFilters(filters);
		return mapping;
	}
	

}
