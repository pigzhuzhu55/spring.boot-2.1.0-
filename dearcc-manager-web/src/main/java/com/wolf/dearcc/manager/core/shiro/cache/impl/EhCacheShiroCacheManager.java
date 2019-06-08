package com.wolf.dearcc.manager.core.shiro.cache.impl;

import com.wolf.dearcc.manager.core.shiro.cache.ShiroCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public class EhCacheShiroCacheManager implements ShiroCacheManager {

    @Autowired
    private EhCacheManager ehCacheManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
