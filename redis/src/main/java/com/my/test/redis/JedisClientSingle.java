package com.my.test.redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.test.callback.WorkCallback;
import java.util.Map;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单机版jedis
 * @author hzl
 * @date 2016/12/8
 */
@Service("jedisClientSingle")
public class JedisClientSingle implements JedisClient{
    /**
     * 连接池,建议使用其他工具注入进来
     */
    private final JedisPool jedisPool;

    @Autowired(required = false)
    public JedisClientSingle(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String get(final String key) {
        return excute(new WorkCallback<String, Jedis>() {
            public String doWorkCallback(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public String set(final String key, final String value) {
        return excute(new WorkCallback<String, Jedis>() {
            public String doWorkCallback(Jedis jedis) {
                return jedis.set(key,value);
            }
        });
    }

    public Long ttl(final String key) {
        return excute(new WorkCallback<Long, Jedis>() {
            public Long doWorkCallback(Jedis jedis) {
                return jedis.ttl(key);
            }
        });
    }

    public String setex(final String key, final String value, final int seconds) {
        return excute(new WorkCallback<String, Jedis>() {
            public String doWorkCallback(Jedis jedis) {
                return jedis.setex(key,seconds,value);
            }
        });
    }
    public Long del(final String key) {
        return excute(new WorkCallback<Long, Jedis>() {
            public Long doWorkCallback(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    public Long expire(final String key, final int timeout) {
        return excute(new WorkCallback<Long, Jedis>() {
            public Long doWorkCallback(Jedis jedis) {
                return jedis.expire(key,timeout);
            }
        });
    }

    public Boolean exists(final String key) {
        return excute(new WorkCallback<Boolean, Jedis>() {
            @Override
            public Boolean doWorkCallback(Jedis jedis) {
                return excute(new WorkCallback<Boolean, Jedis>() {
                    public Boolean doWorkCallback(Jedis jedis) {
                        return jedis.exists(key);
                    }
                });
            }
        });
    }

    public Boolean setNxAndExpire(final String key, final String value, final int seconds) {
        return excute(new WorkCallback<Boolean, Jedis>() {
            public Boolean doWorkCallback(Jedis jedis) {
                Long result = jedis.setnx(key,value);
                return result == 1L && jedis.expire(key, seconds) > 0;
            }
        });
    }

    @Override
    public Map<String, String> getMap(final String key) {
        return excute(new WorkCallback<Map<String, String>, Jedis>() {
            @Override
            public Map<String, String> doWorkCallback(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    @Override
    public boolean setHash(final String key, final Map<String, String> hash) {
        return excute(new WorkCallback<Boolean, Jedis>() {
            @Override
            public Boolean doWorkCallback(Jedis jedis) {
                String result = jedis.hmset(key,hash);
                return result.equals("OK");
            }
        });
    }

    @Override
    public boolean setHash(final String key, Map<String, String> hash, final int timeOut) {
        if (setHash(key,hash)){
            return excute(new WorkCallback<Boolean, Jedis>() {
                @Override
                public Boolean doWorkCallback(Jedis jedis) {
                    return jedis.expire(key,timeOut)>0;
                }
            });
        }
        return false;
    }

    public <T> T excute(WorkCallback<T, Jedis> workCallback) {
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return workCallback.doWorkCallback(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null !=jedis){
                jedis.close();
            }
        }
        return null;
    }


}
