package com.example.springstart.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id //기본키
	@GeneratedValue // 디비 자동생성
	private int id;
	
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY) // -> 지연로딩 방식
	@JsonIgnore //User : Post -> 1 : N  
	private User user;
	
}
