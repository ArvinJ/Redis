package com.my.test.rest;

import java.io.Serializable;



import javax.annotation.Resource;

import com.my.test.redis.JedisClientCluster;




public class BaseActions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4129916783881898297L;
	
	@Resource(name="jedisClientCluster")
	protected JedisClientCluster jedisClient;


}
