package com.my.test.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.my.test.util.DateUtil;


public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("MerchantCardBalanceJob:"+DateUtil.formate(new Date(), DateUtil.YMDHMS_));	
		
	}

}
