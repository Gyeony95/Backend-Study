package com.example.springstart.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//http status code
//2xx -> ok
//4xx -> client err
//5xx -> server err

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(String message) {
		super(message);
	}

}
