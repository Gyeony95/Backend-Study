package com.example.scrapserver.naver;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverModel {
	private String title;
	private String descriptios;
	private String url;
	private Date registDate;
	
	
//	public NaverModel(String title, String description, String url, Date registDate) {
//		this.title = title;
//		this.descriptios = description;
//		this.url = url;
//		this.registDate = registDate;
//	}
}
