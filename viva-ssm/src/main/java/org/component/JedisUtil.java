package org.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class JedisUtil {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	//存储、修改value
	public void addOrUpdate(String key,Object val) {
		redisTemplate.opsForValue().set(key, val);
	}
	//通过key获取value
	public Object load(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	//删除数据
	public void delete(String key) {
		 redisTemplate.delete(key);
	}
	//验证key是否存在
	public boolean check(String key){
		return redisTemplate.hasKey(key);
	}
}
