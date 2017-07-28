package com.my.test.redis;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.JedisCluster;



@Service("jedisClientCluster")
public  class JedisClientCluster implements JedisClient {

	@Autowired
	JedisCluster jedisCluster;

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}
	
	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisCluster.set(key, value);
	}

	@Override
	public Long ttl(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.pttl(key);
	}

	@Override
	public String setex(String key, String value, int seconds) {
		// TODO Auto-generated method stub

		return jedisCluster.setex(key, seconds, value);
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.del(key);
	}

	@Override
	public Long expire(String key, int timeout) {
		// TODO Auto-generated method stub
		return jedisCluster.expire(key, timeout);
	}

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.exists(key);
	}

	@Override
	public Boolean setNxAndExpire(String key, String value, int seconds) {
		// TODO Auto-generated method stub

		long l1 = jedisCluster.setnx(key, value);
		if (l1 < 0) {
			return false;
		}

		long l2 = jedisCluster.expire(key, seconds);

		return l2 > 0;
	}

	@Override
	public Map<String, String> getMap(String key) {
		// TODO Auto-generated method stub
		try {
			String jsonObject = jedisCluster.get(key);
			JSONObject jsObject = JSONObject.parseObject(jsonObject);
			Map<String, String> getMap = new HashMap<String, String>();
			Iterator<String> keys = jsObject.keySet().iterator();
			while (keys.hasNext()) {
				String myKey = keys.next();
				getMap.put(myKey, jsObject.getString(myKey));
			}
			return getMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean setHash(String key, Map<String, String> hash) {
		// TODO Auto-generated method stub
		JSONObject object = (JSONObject) JSONObject.toJSON(hash);
		String result = jedisCluster.set(key, object.toJSONString());
		return org.apache.commons.lang.StringUtils.isEmpty(result)
				&& "nil".equals(result) ? false : true;
	}

	@Override
	public boolean setHash(String key, Map<String, String> hash, int timeOut) {
		boolean _hash = setHash(key, hash);
		if (!_hash) {
			return false;
		}
		long result = expire(key, timeOut);

		return result > 0;
	}

//	@Override
//	public abstract <T> T excute(WorkCallback<T, Jedis> workCallback) ;

}
