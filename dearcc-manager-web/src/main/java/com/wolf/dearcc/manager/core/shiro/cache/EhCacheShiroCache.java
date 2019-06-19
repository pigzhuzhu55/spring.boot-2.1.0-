package com.wolf.dearcc.manager.core.shiro.cache;

import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.ProtostuffUtil;
import com.wolf.dearcc.manager.core.shiro.bo.SimpleSessionEx;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;

import java.util.Collection;
import java.util.Set;

/**
 *
 * 缓存获取Manager
 *
 *
 *
 */
@SuppressWarnings("unchecked")
public class EhCacheShiroCache<K, V> extends ShiroCache implements Cache<K, V> {

    protected static final String SHIRO_CACHE_NAME = "users";

    private EhCacheManager ehCacheManager;


	static final Class<EhCacheShiroCache> SELF = EhCacheShiroCache.class;
    public EhCacheShiroCache(String name, EhCacheManager ehCacheManager) {
        this.setName(name);
        this.ehCacheManager = ehCacheManager;
    }


    @Override
    public V get(K key) throws CacheException {
        try {
            byte[] byteValue = (byte[])ehCacheManager.getCache(SHIRO_CACHE_NAME).get(buildCacheKey(key));
            return (V) ProtostuffUtil.deserialize(byteValue, SimpleSessionEx.class);
        } catch (Exception e) {
            LoggerUtils.error(SELF, "get value by cache throw exception",e);
        }
        return null;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            byte[] byteValue = ProtostuffUtil.serialize(value);
            ehCacheManager.getCache(SHIRO_CACHE_NAME).put(buildCacheKey(key),byteValue);
        } catch (Exception e) {
        	 LoggerUtils.error(SELF, "put cache throw exception",e);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            ehCacheManager.getCache(SHIRO_CACHE_NAME).remove(buildCacheKey(key));
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
