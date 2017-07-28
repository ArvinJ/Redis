package com.my.test.redis;



import java.util.Map;



/**
 * 定义redis需要的接口
 * @date 2016/12/8
 */
public interface JedisClient {
    /**
     * 根据key值获取
     * @param key 指定key
     * @return 存在返回值,不存在返回null
     */
    String get(String key);

    /**
     * 设置值(String kei)
     * @param key key值
     * @param value 内容
     * @return 成功则 ok
     */
    String set(String key, String value);

    /**
     * 得到key的剩余时间
     * @param key 指定key
     * @return -2不存在 -1存在但无剩余时间 其他为剩余时间
     */
    Long ttl(String key);

    /**
     * 等价于 set + expire
     * @param key 设置的key
     * @param seconds 存活时间,到期自动删除
     * @param value 对应值
     * @return 结果
     */
    String setex(final String key, final String value, final int seconds);

    /**
     * 删除指定key
     * @param key 指定key
     * @return 不存在返回0 返回删除个数
     */
    Long del(String key);

    /**
     *给指定key设置时间
     * @param key 指定key
     * @param timeout 超时时间,单位秒
     * @return 1成功 0失败
     */
    Long expire(String key, int timeout);

    /**
     * 检测key是否存在
     * @param key 指定key
     * @return true存在
     */
    Boolean exists(String key);

    /**
     * 不存在则设置,并延时,存在则什么都不做
     * @param key key值
     * @param value 值
     * @param seconds 超时时间
     * @return true成功
     */
    Boolean setNxAndExpire(final String key, final String value, final int seconds);

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     * @param key 指定key
     * @return map中的值
     */
    Map<String, String> getMap(String key);

    /**
     * 设置hash
     * @param key 对应键
     * @param hash 对应hash集合
     * @return true成功
     */
    boolean setHash(String key, Map<String, String> hash);

    /**
     * 设置hash并设置过期时间
     * @param key 对应键
     * @param hash 对应hash集合
     * @param timeOut 过期时间,秒
     * @return true成功
     */
    boolean setHash(String key, Map<String, String> hash,int timeOut);
//    /**
//     * 通用模板方法,很适合提取公共操作
//     * @param workCallback 处理函数
//     * @param <T> 返回类型
//     * @return 结果
//     */
//    <T> T excute(WorkCallback<T, Jedis> workCallback);
}
