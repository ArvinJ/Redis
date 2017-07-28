package com.my.test.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("quartzService")
public class QuartzService {

	@Autowired
	private Scheduler quartzScheduler;

	/**
	  * 添加一个定时器任务
	  * @Description(这里用一句话描述这个方法的作用)
	  * @return void 
	  * @author 
	  */
	public void addJob(String jobName, String jobGroupName, String triggerName,
			String triggerGroupName, Class cls, String cron,String description) {
		try {
			// 获取调度器
			Scheduler sched = quartzScheduler;
			// 创建一项作业
			JobDetail job = JobBuilder.newJob(cls).withDescription(description)
					.withIdentity(jobName, jobGroupName).build();
			// 创建一个触发器
			CronTrigger trigger = TriggerBuilder.newTrigger().withDescription(description)
					.withIdentity(triggerName, triggerGroupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing())
					.build();
			
			// 告诉调度器使用该触发器来安排作业
			sched.scheduleJob(job, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改定时器任务信息
	 */
	public boolean modifyJobTime(String oldjobName, String oldjobGroup,
			String oldtriggerName, String oldtriggerGroup,Class<?> cls,String jobName,
			String jobGroup, String triggerName, String triggerGroup,
			String cron,String description) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(oldtriggerName, oldtriggerGroup));
			if (trigger == null) {
				return false;
			}

			JobKey jobKey = JobKey.jobKey(oldjobName, oldjobGroup);
			TriggerKey triggerKey = TriggerKey.triggerKey(oldtriggerName,oldtriggerGroup);
			// 停止触发器
			sched.pauseTrigger(triggerKey);
			// 移除触发器
			sched.unscheduleJob(triggerKey);
			// 删除任务
			sched.deleteJob(jobKey);

			addJob(jobName, jobGroup, triggerName, triggerGroup, cls, cron,description);

			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	  * 修改一个定时器任务
	  * @Description(这里用一句话描述这个方法的作用)
	  * @return void 
	  * @author 
	  */
	public void modifyJobTime(String triggerName, String triggerGroupName,
			String time) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(triggerName, triggerGroupName));
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.getTriggerBuilder()
						.withSchedule(CronScheduleBuilder.cronSchedule(time))
						.build();
				// 重启触发器
				sched.resumeTrigger(TriggerKey.triggerKey(triggerName,
						triggerGroupName));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	  * 移除一个定时器任务
	  * @Description(这里用一句话描述这个方法的作用)
	  * @return void 
	  * @author 
	  */
	public void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = quartzScheduler;
			// 停止触发器
			sched.pauseTrigger(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 移除触发器
			sched.unscheduleJob(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 删除任务
			sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	  * 开启定时器
	  * @Description(这里用一句话描述这个方法的作用)
	  * @return void 
	  * @author 
	  */
	public void startSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	  * 停止定时器
	  * @Description(这里用一句话描述这个方法的作用)
	  * @return void 
	  * @author 
	  */
	public void shutdownSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	  * 暂停定时器
	  * @Description(这里用一句话描述这个方法的作用)
	  * @return void 
	  * @author 
	  */
	public void pauseJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	/**
	  * 恢复 定时器
	  * @Description(这里用一句话描述这个方法的作用)
	  * @return void 
	  * @author 
	  */
	public void resumeJob(String jobName, String jobGroupName) {
		try {
			quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}