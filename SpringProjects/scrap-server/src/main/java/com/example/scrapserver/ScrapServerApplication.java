package com.example.scrapserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrapServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrapServerApplication.class, args);
		
		
//		String url = "http://wikibook.co.kr";
		String url = "https://careers.kakao.com/jobs";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements elements = doc.select("div.kakaoWrap");
			System.out.println("asdasd : "+elements.toString());
			for(Element element : elements) {
				System.out.println("asdasd : "+element.toString());

			}
			
			
			
			
			
			
//			Elements elements = doc.select("li.book-in-front a");
//			
//			for(Element element : elements) {
//				String title = element.text().trim();
//				String nextUrl = element.attr("href");
//				Document nextDoc = Jsoup.connect(nextUrl).get();
//				String content = nextDoc.select("div.tab-content").html();
//				System.out.println("asdasda : "+content);
//				Files.write(Paths.get(title+".html"), content.getBytes("UTF-8"));
//			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
