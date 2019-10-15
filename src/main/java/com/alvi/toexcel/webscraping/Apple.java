package com.alvi.toexcel.webscraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alvi.toexcel.model.Job;
import com.alvi.toexcel.service.JobService;

import lombok.extern.slf4j.Slf4j;

/**
 * Apple job site parsing class. <br>
 * URL: https://jobs.apple.com/en-us/search?page=1
 * 
 * @author tanmoy.tushar
 * @since 2019-04-28
 */
@Slf4j
public class Apple extends JobService {
	private static final String URL = "https://jobs.apple.com/en-us/search?page=1";
	private static final String ADD_URL = "/en-us/search?page=";
	private String baseUrl;

	public void getScrapedJobs() throws IOException, InterruptedException {
		log.info("====Scraper started====");
		List<Job> jobList = new ArrayList<Job>();
		this.baseUrl = URL.substring(0, 22);
		Document doc = Jsoup.connect(URL).timeout(100000).get();
		int totalP = getTotalPage(doc);
		for (int i = 1; i <= totalP; i++) {
			log.info("Scraping on page " + i);
			String url = getBaseUrl() + ADD_URL + i;
			try {
				jobList.addAll(browseJobList(url));
			} catch (Exception e) {
				log.warn("Failed to parse job list page of " + url);
			}
		}
		log.info("====Scraper Finished====");
		setJobList(jobList);
	}

	private List<Job> browseJobList(String url) throws IOException {
		List<Job> jobList = new ArrayList<Job>();
		Document doc = Jsoup.connect(url).timeout(10000).get();
		Elements rowList = doc.select("table[id=tblResultSet]>tbody>tr>td>a");
		for (Element row : rowList) {
			String jobUrl = getBaseUrl() + row.attr("href");
			try {
				jobList.add(getJobDetail(jobUrl));					
			} catch (Exception e) {
				log.warn("Failed to parse job detail of " + jobUrl);
			}
		}
		return jobList;
	}

	private Job getJobDetail(String url) throws IOException {
		Document doc = Jsoup.connect(url).timeout(10000).get();
		Job job = new Job(url);
		Element jobE = doc.selectFirst("h1");
		job.setTitle(jobE.text());
		job.setCategory("APPLE RETAIL"); //Category not provided in job site
		job.setType("Full-Time"); //Type not provided in job site
		job.setRefId(job.getJobUrl().substring(37, 46));
		return job;
	}

	private int getTotalPage(Document doc) {
		Element totalPage = doc.select("span[class=pageNumber]").get(1);
		return Integer.parseInt(totalPage.text().trim());
	}

	protected String getBaseUrl() {
		return baseUrl;
	}
}
