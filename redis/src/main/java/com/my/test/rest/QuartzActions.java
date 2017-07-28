package com.my.test.rest;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.test.service.QuartzService;
import com.my.test.util.DateUtil;


@Component
@Produces("application/json;charset=UTF-8")
@Path("/quartz")
public class QuartzActions {
	Logger logger = LoggerFactory.getLogger(QuartzActions.class);
	
	@Autowired
	private Scheduler quartzScheduler;
	@Autowired
	private QuartzService quartzService;


	/**
	 * 添加定时任务
	 * @param version
	 * @param source
	 * @param cron  cron表达式
	 * @param description  描述
	 * @param jobName  定时任务名
	 * @param jobGroupName  任务组名
	 * @param triggerName  触发器名
	 * @param triggerGroupName 触发器组名
	 * @param clazz 任务类名
	 * @return
	 * @throws Exception
	 * 
	 * http(s)://IP:PORT/job-app/rest/quartz/job/add
	 */
	@POST
	@Path("job/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addJob(@FormParam("version") String version,
			@FormParam("source") String source,
			@CookieParam("userid") String userid,
			@CookieParam("uniidentifier") String uniidentifier,
			@FormParam("cron") String cron, //cron表达式
			@FormParam("description") String description,//描述
			@FormParam("jobname") String jobName,//定时任务名
			@FormParam("jobgroupname") String jobGroupName,//任务组名
			@FormParam("triggername") String triggerName,//触发器名
			@FormParam("triggergroupname") String triggerGroupName,//触发器组名
			@FormParam("clazz") String clazz//任务类名
			) throws Exception {
			System.out.println("job/add");
			JSONObject rest = new JSONObject();
			if(StringUtils.isEmpty(cron)||StringUtils.isEmpty(description)||StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobGroupName)||StringUtils.isEmpty(triggerName)||StringUtils.isEmpty(triggerGroupName)||StringUtils.isEmpty(clazz)){
				rest.put("status", "1");
				rest.put("msg", "参数缺失");
				return rest.toString();
			}
			Class<?> cls = Class.forName(clazz);
			quartzService.addJob(jobName, jobGroupName, triggerName,triggerGroupName, cls, cron,description);
			rest.put("status", "0");
			rest.put("msg", "成功");
			return rest.toString();
	}
	
	/**
	 * 修改定时任务
	 * @param version
	 * @param source
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @return
	 * @throws Exception
	 * 
	 * http(s)://IP:PORT/job-app/rest/quartz/job/edit
	 */
	@POST
	@Path("job/edit")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String editJob(@FormParam("version") String version,
			@FormParam("source") String source,
			@CookieParam("userid") String userid,
			@CookieParam("uniidentifier") String uniidentifier,
			@FormParam("cron") String cron, //cron表达式
			@FormParam("description") String description,//描述
			@FormParam("jobname") String jobName,//定时任务名
			@FormParam("jobgroupname") String jobGroupName,//任务组名
			@FormParam("triggername") String triggerName,//触发器名
			@FormParam("triggergroupname") String triggerGroupName,//触发器组名
			@FormParam("clazz") String clazz//任务类名
			) throws Exception {
			JSONObject rest = new JSONObject();
			if(StringUtils.isEmpty(cron)||StringUtils.isEmpty(description)||StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobGroupName)||StringUtils.isEmpty(triggerName)||StringUtils.isEmpty(triggerGroupName)||StringUtils.isEmpty(clazz)){
				rest.put("status", "1");
				rest.put("msg", "参数缺失");
				return rest.toString();
			}
			System.out.println(cron);
			Class<?> cls = Class.forName(clazz);
			quartzService.modifyJobTime(jobName, jobGroupName, triggerName, triggerGroupName, cls, jobName, jobGroupName, triggerName, triggerGroupName, cron, description);
			rest.put("status", "0");
			rest.put("msg", "成功");
			return rest.toString();
	}
	
	
	/**
	 * 移除定时任务
	 * @param version
	 * @param source
	 * @param jobName  定时任务名
	 * @param jobGroupName  任务组名
	 * @param triggerName  触发器名
	 * @param triggerGroupName 触发器组名
	 * @return
	 * @throws Exception
	 * 
	 *  http(s)://IP:PORT/job-app/rest/quartz/job/remove
	 */
	@POST
	@Path("job/remove")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String removeJob(@FormParam("version") String version,
			@FormParam("source") String source,
			@CookieParam("userid") String userid,
			@CookieParam("uniidentifier") String uniidentifier,
			@FormParam("jobname") String jobName,//定时任务名
			@FormParam("jobgroupname") String jobGroupName,//任务组名
			@FormParam("triggername") String triggerName,//触发器名
			@FormParam("triggergroupname") String triggerGroupName//触发器组名
			) throws Exception {
			JSONObject rest = new JSONObject();
			if(StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobGroupName)||StringUtils.isEmpty(triggerName)||StringUtils.isEmpty(triggerGroupName)){
				rest.put("status", "1");
				rest.put("msg", "参数缺失");
				return rest.toString();
			}
			quartzService.removeJob(jobName, jobGroupName,triggerName,triggerGroupName);
			rest.put("status", "0");
			rest.put("msg", "成功");
			return rest.toString();
	}
	
	/**
	 * 暂停定时任务
	 * @param version
	 * @param source
	 * @param jobName  定时任务名
	 * @param jobGroupName  任务组名
	 * @return
	 * @throws Exception
	 * 
	 *  http(s)://IP:PORT/job-app/rest/quartz/job/pause
	 */
	@POST
	@Path("job/pause")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String pauseJob(@FormParam("version") String version,
			@FormParam("source") String source,
			@CookieParam("userid") String userid,
			@CookieParam("uniidentifier") String uniidentifier,
			@FormParam("jobname") String jobName,//定时任务名
			@FormParam("jobgroupname") String jobGroupName//任务组名
			) throws Exception {
		JSONObject rest = new JSONObject();
		if(StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobGroupName)){
			rest.put("status", "1");
			rest.put("msg", "参数缺失");
			return rest.toString();
		}
		quartzService.pauseJob(jobName, jobGroupName);
		rest.put("status", "0");
		rest.put("msg", "成功");
		return rest.toString();
	}
	
	/**
	 * 恢复定时任务
	 * @param version
	 * @param source
	 * @param jobName  定时任务名
	 * @param jobGroupName  任务组名
	 * @return
	 * @throws Exception
	 * 
	 *  http(s)://IP:PORT/job-app/rest/quartz/job/resume
	 */
	@POST
	@Path("job/resume")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String resumeJob(@FormParam("version") String version,
			@FormParam("source") String source,
			@CookieParam("userid") String userid,
			@CookieParam("uniidentifier") String uniidentifier,
			@FormParam("jobname") String jobName,//定时任务名
			@FormParam("jobgroupname") String jobGroupName//任务组名
			) throws Exception {
		JSONObject rest = new JSONObject();
		if(StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobGroupName)){
			rest.put("status", "1");
			rest.put("msg", "参数缺失");
			return rest.toString();
		}
		quartzService.resumeJob(jobName, jobGroupName);
		rest.put("status", "0");
		rest.put("msg", "成功");
		return rest.toString();
	}
	
	/**
	 * 查询定时任务
	 * @param version
	 * @param source
	 * @param userid
	 * @param uniidentifier
	 * @return
	 * @throws SchedulerException
	 * 
	 * 
	 *  http(s)://IP:PORT/job-app/rest/quartz/query/detail
	 */
	@GET
	@Path("query/detail")
	public String querydetail(
			@QueryParam("version") String version,
			@QueryParam("source") String source,
			@CookieParam("userid") String userid,
			@CookieParam("uniidentifier") String uniidentifier
			) throws SchedulerException {
		// 构造返回对象
		JSONObject res = new JSONObject();
		JSONArray data = new JSONArray();

		List<String> triggerGroupNames = quartzScheduler.getTriggerGroupNames();
		for (String triggerGroupName : triggerGroupNames) {
			Set<TriggerKey> triggerKeySet = quartzScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupName));
			for (TriggerKey triggerKey : triggerKeySet) {
				Trigger t = quartzScheduler.getTrigger(triggerKey);
				if (t instanceof CronTrigger) {
					CronTrigger trigger = (CronTrigger) t;
					JobKey jobKey = trigger.getJobKey();
					JobDetail jd = quartzScheduler.getJobDetail(jobKey);
					JSONObject oneJsone = new JSONObject();
					oneJsone.put("JobName", jobKey.getName());//任务名称
					oneJsone.put("JobGroup", jobKey.getGroup());//任务组名
					oneJsone.put("TriggerName", triggerKey.getName());//触发器名称
					oneJsone.put("TriggerGroupName", triggerKey.getGroup());//触发器组名称
					oneJsone.put("CronExpr", trigger.getCronExpression());//时间表达式
					oneJsone.put("PreviousFireTime",trigger.getPreviousFireTime()!=null?DateUtil.formate(trigger.getPreviousFireTime(), DateUtil.YMDHMS_):"");//上次运行时间
					oneJsone.put("NextFireTime",trigger.getNextFireTime()!=null?DateUtil.formate(trigger.getNextFireTime(), DateUtil.YMDHMS_):""); //下次运行时间
					oneJsone.put("StartTime",trigger.getStartTime()!=null?DateUtil.formate(trigger.getStartTime(), DateUtil.YMDHMS_):"");//开始时间
					oneJsone.put("EndTime",trigger.getEndTime()!=null?DateUtil.formate(trigger.getEndTime(), DateUtil.YMDHMS_):"");//结束时间
					oneJsone.put("JobDescription", jd.getDescription());//任务描述
					oneJsone.put("JobClass", jd.getJobClass().getCanonicalName());//任务类名
					Trigger.TriggerState triggerState = quartzScheduler.getTriggerState(trigger.getKey());
					// NONE无,// NORMAL正常,// PAUSED暂停,// COMPLETE完全,// ERROR错误,// BLOCKED阻塞
					oneJsone.put("JobStatus",triggerState.toString());//任务状态
					data.add(oneJsone);
				}
			}
		}
		
		res.put("status", "0");
		res.put("msg", "成功");
		res.put("rows", data);

		return res.toString();

	}
}
