package com.my.test.quartzJob;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.my.test.util.DateUtil;


public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("TestJob:"+DateUtil.formate(new Date(), DateUtil.YMDHMS_));
		
	}

}
