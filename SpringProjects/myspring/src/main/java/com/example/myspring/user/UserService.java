package com.example.myspring.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class UserService {

	private static List<User> users = new ArrayList<>();
	
	//데이터베이스를 사용하지 않기때문에 기존 User의 목록을 불러오려면 더미 데이터를 넣어주어야 합니다.
	static {
		users.add(new User("id1", "pw1"));
		users.add(new User("id2", "pw1"));
		users.add(new User("id3", "pw1"));
	};
	
	//전체사용자 조회
	public List<User> getAllUser(){
		return users;
	}
	
	//아이디를 통한 개별사용자 조회
	public User getUser(String id) {
		for(User user : users) {
			if(user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}
	
	//유저정보를 추가합니다. 
	public void addUser(String id, String pw) {
		users.add(new User(id, pw));
	}
	
	//검색한 유저의 pw를 수정합니다.
	public void updateUser(String id, String modPw) {
		for(User user : users) {
			if(user.getId().equals(id)) {
				user.setPw(modPw);
			}
		}
	}
	
	//아이디로 유저를 검색해 삭제합니다.
	public void deleteUser(String id) {
		java.util.Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			
			if(user.getId().equals(id)) {
				iterator.remove();
			}
		}
	}
	
	
	
}
