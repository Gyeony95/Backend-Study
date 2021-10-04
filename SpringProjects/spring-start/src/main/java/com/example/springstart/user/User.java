package com.example.springstart.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value= {"password"})//json 데이터 보낼때 빼고 보냄
//@JsonFilter("UserInfo")
@Entity //db 테이블로 자동으로생성되게 함
@ApiModel(description = "사용자 상세정보를 위한 도메인 객체")
public class User {
	
	@Id //@Entity와 세트 
	@GeneratedValue
	private Integer id;
	
	@Size(min=2, message = "Name은 2글자 이상 전달해 주세요")
	@ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
	private String name;
//	@Past
//	@ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.")
//	private Date joinDate;
	
	@ApiModelProperty(notes = "사용자 패스워드을 입력해 주세요.")
	private String password;
	@ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요.")
	private String ssn;
	
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	public User(int id, String name, String password, String ssn) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.ssn = ssn;
	}
}
