package com.example.springstart.user;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value= {"password"})//json 데이터 보낼때 빼고 보냄
//@JsonFilter("UserInfo")
public class User {
	private Integer id;
	
	@Size(min=2, message = "Name은 2글자 이상 전달해 주세요")
	private String name;
	@Past
	private Date joinDate;
	
	private String password;
	private String ssn;
}
