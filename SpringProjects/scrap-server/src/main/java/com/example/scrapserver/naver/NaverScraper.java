package com.example.scrapserver.naver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverScraper {
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
    private final String URL = "https://recruit.navercorp.com/naver/job/list/developer"; //네이버 커리어 -> 개발
    private final String domain = "https://recruit.navercorp.com/"; //도메인 URL 
    private final String saveFileName = "/scraping/scraping.json";
    private final String saveFolderPath = "/scraping";
    private String userProjectPath = "/blah/blah";

    
    /**
     * 크롤링
     * @return
     */
    public List<NaverModel> parseHTMLData() {
        List<NaverModel> scrapers = new ArrayList<>();
        try {
            // timeout을 설정하지 않으면 ReadTimeoutException이 발생할 수 있다.
            Document doc = Jsoup.connect(URL).timeout(50000).get(); 
          
            // class 명이 title이고, 그 아래에 있는 a 태그들을 elements에 담는다.
            Elements elements = doc.select(".title > a");
            for(Element element : elements) {
                  // JSON 객체로 변환할 DTO
            	NaverModel scraper = new NaverModel();
                  // a 태그의 href 속성에 있는 값을 가져온다.
                  String link = element.attr("href");
                  String title = element.text();
                  scraper.setUrl(domain + link);
                  scraper.setTitle(title);
            }
            return convertScraperDto(scrapers);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    /**
     * ObjectMapper로 Convert
     * @param scarpers
     * @return
     */
    private List<NaverModel> convertScraperDto(List<NaverModel> scarpers) {
        // List<ScraperDto>의 형태로 convert
        List<NaverModel> convertedList = objectMapper.convertValue(scarpers, new TypeReference<List<NaverModel>>() {});
        return convertedList;
    }
    
    /**
     * JSON String 타입으로 
     * @param convertedList
     * @return
     */
    private String convertToString (List<NaverModel> convertedList) {
        try {
            // Json String 타입으로 변환
            return objectMapper.writeValueAsString(convertedList);
        } catch (JsonProcessingException e) {
            log.error("Convert Error");
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON 파일 생성
     */
    public void createJsonToScraper() {
        String jsonString = convertToString(parseHTMLData());
        System.out.println("ghghgh : "+jsonString );
        try {
            File saveFolder = new File(userProjectPath+saveFolderPath);
            File saveJsonFile = new File(userProjectPath, saveFileName);
            if(!saveFolder.exists() || saveFolder.isFile()) {
                if(!saveFolder.mkdirs()) {
                    throw new IOException("디렉터리 생성 실패");
                }
            }
            FileUtils.writeStringToFile(saveJsonFile, jsonString, "UTF-8");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON 파일 읽기
     * @return
     */
    public List readScraperJson() {
        Resource resource = resourceLoader.getResource(saveFileName);
        if (!resource.exists()) {
            log.error("해당 json 파일이 존재하지 않습니다.");
        }
        try {
            File file = resource.getFile();
//            String jsonString = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
            String jsonString = FileUtils.readFileToString(file, "UTF-8");
            return objectMapper.readValue(jsonString, new TypeReference<List<NaverModel>>() {});
        } catch (IOException e) {
            log.error("JSON 파일 읽는중 예외 발생", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
}
