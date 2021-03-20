package com.buildit.crawler.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.buildit.crawler.pojo.SiteMapper;

@Service
public class CrawlRoutineService {

	ConcurrentHashMap<String, Boolean> visitedMap = new ConcurrentHashMap<>();

	CopyOnWriteArrayList<SiteMapper> visitedLinks = new CopyOnWriteArrayList<>();

	ExecutorService executorService;

	AtomicInteger count = new AtomicInteger(0);

	/**
	 * Initialize and start crawl routine
	 * @param url	
	 * @return
	 */
	public synchronized CopyOnWriteArrayList<SiteMapper> fetchCrawl(String url) {
		visitedLinks = new CopyOnWriteArrayList<>();
		executorService = Executors.newCachedThreadPool();
		count = new AtomicInteger(0);
		try {
			executorService.execute(new CrawlThread(url));
			executorService.awaitTermination(5, TimeUnit.MINUTES);
			return visitedLinks;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visitedLinks;

	}

	class CrawlThread implements Runnable {

		String mainLink;

		CrawlThread(String link) {
			this.mainLink = link;
		}

		@Override
		public void run() {
			count.incrementAndGet();
			try {
				Document doc = checkConnection(mainLink);
				if (doc != null) {
					for (Element link : doc.select("a[href]")) {
						String nextLink = link.absUrl("href");
						// Check existance of the link
						if (!visitedMap.contains(nextLink)) {
							visitedLinks.add(new SiteMapper(nextLink));
							visitedMap.put(nextLink, true);
							
							if (nextLink.contains("://wiprodigital.com")) {
								executorService.execute(new CrawlThread(nextLink));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(5_000);
					if (count.decrementAndGet() == 0 && !executorService.isShutdown())
						executorService.shutdown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Check the document connection
		 * @param url
		 * @return
		 */
		public Document checkConnection(String url) {
			try {
				Connection conn = Jsoup.connect(url);
				Document doc = conn.get();
				if (conn.response().statusCode() == 200) {
					return doc;
				}
			} catch (IOException e) {

			}
			return null;
		}

	}

}
