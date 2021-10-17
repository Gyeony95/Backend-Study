package com.example.myspring.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class User {
	private String id;
	private String pw;
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
}
