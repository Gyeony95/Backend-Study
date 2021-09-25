package com.example.springstart.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor//생성자 대신임
class HelloWorldBean {
	private String messege;
	
}
