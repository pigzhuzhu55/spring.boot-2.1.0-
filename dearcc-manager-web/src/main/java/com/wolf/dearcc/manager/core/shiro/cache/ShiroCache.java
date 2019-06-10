package com.wolf.dearcc.manager.core.shiro.cache;

public abstract class ShiroCache {


    /**
     * 前缀
     */
    protected static final String SHIRO_CACHE_PRE = "SHC:";

    private String name;

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

    protected String buildCacheKey(Object key) {
        return SHIRO_CACHE_PRE + getName() + ":" + key;
    }
}
