package com.alvi.toexcel;

import java.io.IOException;

import com.alvi.toexcel.service.WriteService;
import com.alvi.toexcel.webscraping.Apple;

/**
 * Main class of Converting Web Data to Excel
 * 
 * @author tanmoy.tushar
 * @since 2019-10-15
 */
public class Application {
	public static final String FILE_PATH = "C:\\Users\\tanmoy.tushar\\Desktop\\TestExcel.xlsx";

	public static void main(String[] args) throws IOException, InterruptedException {
		Apple apple = new Apple();
		apple.getScrapedJobs();
		WriteService ws = new WriteService();
		ws.wirteExcel(apple.getJobList(), FILE_PATH);
	}
}
