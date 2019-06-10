package com.wolf.dearcc.manager.core.shiro.cache;

import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.ProtostuffUtil;
import com.wolf.dearcc.manager.core.shiro.bo.SimpleSessionEx;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collection;
import java.util.Set;

/**
 *
 * 缓存获取Manager
 *
 * 
 */
@SuppressWarnings("unchecked")
public class RedisShiroCache<K, V> extends ShiroCache implements Cache<K, V> {

    private RedisTemplate redis;

	static final Class<RedisShiroCache> SELF = RedisShiroCache.class;
    public RedisShiroCache(String name, RedisTemplate redis) {
        this.setName(name);
        this.redis = redis;
    }

    @Override
    public V get(K key) throws CacheException {
        byte[] byteValue = new byte[0];
        try {
            ValueOperations<String,Object> operations = redis.opsForValue();
            byteValue = (byte[])operations.get(buildCacheKey(key));
        } catch (Exception e) {
            LoggerUtils.error(SELF, "get value by cache throw exception",e);
        }
        return (V) ProtostuffUtil.deserialize(byteValue,SimpleSessionEx.class);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            byte[] byteValue = ProtostuffUtil.serialize(value);
            ValueOperations<String,Object> operations = redis.opsForValue();
            operations.set(buildCacheKey(key),byteValue);
        } catch (Exception e) {
        	 LoggerUtils.error(SELF, "put cache throw exception",e);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            ValueOperations<String,Object> operations = redis.opsForValue();
            operations.getOperations().delete(buildCacheKey(key));
        } catch (Exception e) {
            LoggerUtils.error(SELF, "remove cache  throw exception",e);
        }
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        //TODO--
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        //TODO
        return null;
    }

    @Override
    public Collection<V> values() {
        //TODO
        return null;
    }


}
