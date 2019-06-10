package com.wolf.dearcc.manager.core.shiro.cache;

import com.alibaba.fastjson.JSONObject;
import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.ProtostuffUtil;
import com.wolf.dearcc.common.utils.SerializeUtil;
import com.alibaba.fastjson.JSON;
import com.wolf.dearcc.manager.core.shiro.bo.SimpleSessionEx;
import com.wolf.dearcc.manager.core.shiro.session.CustomSessionManager;
import com.wolf.dearcc.manager.core.shiro.session.SessionStatus;
import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Console;
import java.io.Serializable;
import java.util.Collection;


public class EhCacheShiroSessionRepository implements ShiroSessionRepository {

    public static final String SHIRO_SESSION_PRE = "SHSS:";
    public static final String SHIRO_SESSION_PRE_ALL = "*SHSS:*";
    private static final String SHIRO_CACHE_NAME = "session";

    @Autowired
    private EhCacheManager ehCacheManager;

    public EhCacheShiroSessionRepository(EhCacheManager ehCacheManager){
        this.ehCacheManager=ehCacheManager;
    }

    public EhCacheManager getEhCacheManager() {
        return ehCacheManager;
    }

    public void setEhCacheManager(EhCacheManager ehCacheManager) {
        this.ehCacheManager = ehCacheManager;
    }

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            String key = buildEhCacheSessionKey(session.getId());

            //不存在才添加。
            if(null == session.getAttribute(CustomSessionManager.SESSION_STATUS)){
                //Session 踢出自存存储。
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }

            //LoggerUtils.debug(getClass(), JSON.toJSONString(session));
            byte[] value = ProtostuffUtil.serialize(session);
            ehCacheManager.getCache(SHIRO_CACHE_NAME).put(key,value);

        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "save session error，id:[%s]",session.getId());
        }
    }

    @Override
    public void deleteSession(Serializable sessionId) {
        if (sessionId == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            String key = buildEhCacheSessionKey(sessionId);
            ehCacheManager.getCache(SHIRO_CACHE_NAME).remove(key);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]",sessionId);
        }
    }

    @Override
    public Session getSession(Serializable sessionId) {
        if (sessionId == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {

            String key = buildEhCacheSessionKey(sessionId);
            byte[] value = (byte[])ehCacheManager.getCache(SHIRO_CACHE_NAME).get(key);
            session = ProtostuffUtil.deserialize(value, SimpleSessionEx.class);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]",sessionId);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
       return null;
    }

    @Override
    public String getSessonId(String userId) {
        return null;
    }

    @Override
    public void deleteSessionId(String userId) {

    }

    @Override
    public void setSessionId(String userId, String sessionId) {

    }

    private String buildEhCacheSessionKey(Serializable sessionId) {
        return SHIRO_SESSION_PRE + sessionId;
    }
}
