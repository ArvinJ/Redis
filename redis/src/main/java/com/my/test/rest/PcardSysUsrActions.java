package com.my.test.rest;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.test.constant.AESKeyConstant;
import com.my.test.entity.PcardSysUsr;
import com.my.test.redis.JedisClientCluster;



import com.my.test.repository.PcardSysUsrRepository;
import com.my.test.util.AESEncryptUtil;
import com.my.test.util.DigestUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;















import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Produces("application/json;charset=UTF-8")
@Path("/usr")
public class PcardSysUsrActions extends BaseActions {
	@Autowired
	protected JedisClientCluster jedisClient;
	
	@Autowired 
	PcardSysUsrRepository pcardSysUsrRepository;
	private static final long serialVersionUID = -5972588854123540839L;
	Logger logger = LoggerFactory.getLogger(PcardSysUsrActions.class);
	@POST
	@Path("/sys/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String login(
			@FormParam("version") String version,
			@FormParam("source") String source,
			@FormParam("usrcode") String usrcode,
			@FormParam("usrpasswd") String usrpasswd,
			@FormParam("rolesystem") String rolesystem
			) throws Exception {
		JSONObject res = new JSONObject();
		String status = "0";
		String msg = "登录成功";
		if (usrpasswd == null || usrcode == null) {
			status = "1";
			msg = "用户或密码不能为空";
			res.put("status", status);
			res.put("msg", msg);
			return res.toString();
		}
		Map<String, String> sessionMap = new HashMap<String, String>();
		sessionMap.put("usrcode", usrcode);
		sessionMap.put("usrid", "1109151");
		sessionMap.put("usrmnt", "1109");
//		sessionMap.put("usrrole", pcardSysUsr.getUsrRole());
		// 生成一个随机的用户登录标识
		String uniIdentifier = DigestUtil.getNextHexRandomNum(32);
		// 根据用户名，生成加密后的用户id
		String userId = AESEncryptUtil.base64AndEncrypt(usrcode,
				AESKeyConstant.DF_KEY);
		System.out.println(pcardSysUsrRepository);
		PcardSysUsr psu=	pcardSysUsrRepository.findByUsrCode(usrcode);
		if(psu==null){
			status = "1";
			msg = "用户或密码不正确";
			res.put("status", status);
			res.put("msg", msg);
			return res.toString();
		}

		
		msg=psu.getUsrCode()+psu.getUsrNm();
		jedisClient.setHash(uniIdentifier, sessionMap, 600);
		
		return "hello"+msg+"   "+   uniIdentifier;
	
		
	}

	
	
}
