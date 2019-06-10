package com.wolf.dearcc.manager.core.shiro.cache.impl;

import com.wolf.dearcc.manager.core.shiro.cache.RedisShiroCache;
import com.wolf.dearcc.manager.core.shiro.cache.ShiroCacheManager;
import org.apache.shiro.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * JRedis管理
 *
 * 
 */
public class RedisShiroCacheManager implements ShiroCacheManager {

    private RedisTemplate redis;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new RedisShiroCache<K, V>(name, getRedisTemplate());
    }

    @Override
    public void destroy() {
    	//如果和其他系统，或者应用在一起就不能关闭
    	//getJedisManager().getJedis().shutdown();
    }

    public RedisTemplate getRedisTemplate() {
        return redis;
    }

    public void setRedisTemplate(RedisTemplate redis) {
        this.redis = redis;
    }
}
