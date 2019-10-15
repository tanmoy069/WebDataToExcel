package com.alvi.toexcel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Job 
{
	private String title;
	private String category;
	private String type;
	private String refId;
	private String jobUrl;
	
	public Job(String url) {
		this.jobUrl = url;
	}
}
