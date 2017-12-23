package com.xyz.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisDao {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 根据 key 获得值
	 * 
	 * @param key
	 * @return
	 */
	public <T> T get(String key) {
		return (T) redisTemplate.opsForValue().get(key);
	}

	/**
	 * 设置 key ，value
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 根据 key 删除
	 * 
	 * @param key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}
}
