package com.example.scrapserver.naver;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverScrapScheduler {
	private final NaverScraper naverScraper;
	
	@Scheduled(cron= "0 0 1 1 * ?")
	public void runningScraperJson() {
		naverScraper.createJsonToScraper();
	}
}
