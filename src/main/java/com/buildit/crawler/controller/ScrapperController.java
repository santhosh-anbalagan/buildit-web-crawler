package com.buildit.crawler.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buildit.crawler.pojo.SiteMapper;
import com.buildit.crawler.service.CrawlRoutineService;

@RestController
public class ScrapperController {
	
	@Autowired
	CrawlRoutineService  crawlService;
	
	@GetMapping(value="/crawl")
	public ResponseEntity<?> fetch() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(crawlService.fetchCrawl("http://wiprodigital.com"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	

}
