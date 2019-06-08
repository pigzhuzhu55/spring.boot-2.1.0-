package com.wolf.dearcc.manager.core.shiro.cache;

import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.ProtostuffUtil;
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
 */
@SuppressWarnings("unchecked")
public class EhCacheShiroCache<K, V> implements Cache<K, V> {

	/**
	 *
	 */
    private static final String SHIRO_CACHE_NAME = "users";

    /**
     * 前缀
     */
    private static final String SHIRO_CACHE_PRE = "SHC:";

    private EhCacheManager ehCacheManager;

    private String name;


	static final Class<EhCacheShiroCache> SELF = EhCacheShiroCache.class;
    public EhCacheShiroCache(String name, EhCacheManager ehCacheManager) {
        this.name = name;
        this.ehCacheManager = ehCacheManager;
    }

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public V get(K key) throws CacheException {
        try {
            byte[] byteValue = (byte[])ehCacheManager.getCache(SHIRO_CACHE_NAME).get(buildCacheKey(key));
            return (V) ProtostuffUtil.deserializer(byteValue,Object.class);
        } catch (Exception e) {
            LoggerUtils.error(SELF, "get value by cache throw exception",e);
        }
        return null;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            ehCacheManager.getCache(SHIRO_CACHE_NAME).put(key,value);
        } catch (Exception e) {
        	 LoggerUtils.error(SELF, "put cache throw exception",e);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            ehCacheManager.getCache(SHIRO_CACHE_NAME).remove(key);
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

    private String buildCacheKey(Object key) {
        return SHIRO_CACHE_PRE + getName() + ":" + key;
    }

}
