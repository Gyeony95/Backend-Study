package com.example.springstart.user;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
	
	
//	@GetMapping(path = "/v1/users/{id}") //url 버전관리
//	@GetMapping(path = "/users/{id}/", params = "version=1") //파라미터 버전관리
//	@GetMapping(path = "/users/{id}", headers = "X-API-VERSION=1") // 헤더 버전관리
	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") //마임타입 버전관리
	public MappingJacksonValue retrieveUserV1(@PathVariable int id){
		User user = service.fiundOne(id);
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		UserV2 userV2 = new UserV2(null);
		BeanUtils.copyProperties(user, userV2);
		
		userV2.setGrade("VIP");
		
		
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter //데이터 필터링
				.filterOutAllExcept("id","name", "password","grade");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);// 필터를 사용할수 있는 값으로 바꿔줌
		
		MappingJacksonValue mapping = new MappingJacksonValue(userV2);
		mapping.setFilters(filters);
		return mapping;
	}
	
//	@GetMapping(path = "/v2/users/{id}")
//	@GetMapping(path = "/users/{id}/", params = "version=2")
//	@GetMapping(path = "/users/{id}", headers = "X-API-VERSION=2")
	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
	public MappingJacksonValue retrieveUserV2(@PathVariable int id){
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
