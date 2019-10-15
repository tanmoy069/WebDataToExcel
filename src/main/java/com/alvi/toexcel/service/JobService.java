package com.alvi.toexcel.service;

import java.util.ArrayList;
import java.util.List;

import com.alvi.toexcel.model.Job;

/**
 * Service class for Job
 * 
 * @author tanmoy.tushar
 * @since 2015-10-15
 */
public class JobService {
	
	private List<Job> jobList = new ArrayList<Job>();
	
	public void setJobList(List<Job> list) {
		for (Job job : list) {
			jobList.add(job);
		}
	}
	
	public List<Job> getJobList() {
		return jobList;
	}
}
