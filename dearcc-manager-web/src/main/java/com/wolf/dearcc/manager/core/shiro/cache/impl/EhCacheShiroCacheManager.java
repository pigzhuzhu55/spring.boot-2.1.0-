package com.wolf.dearcc.manager.core.shiro.cache.impl;

import com.wolf.dearcc.manager.core.shiro.cache.EhCacheShiroCache;
import com.wolf.dearcc.manager.core.shiro.cache.ShiroCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public class EhCacheShiroCacheManager implements ShiroCacheManager {

    private EhCacheManager ehCacheManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new EhCacheShiroCache<K,V>(name,ehCacheManager);
    }

    @Override
    public void destroy() {

    }

    public EhCacheManager getEhCacheManager() {
        return ehCacheManager;
    }

    public void setEhCacheManager(EhCacheManager ehCacheManager) {
        this.ehCacheManager = ehCacheManager;
    }
}
