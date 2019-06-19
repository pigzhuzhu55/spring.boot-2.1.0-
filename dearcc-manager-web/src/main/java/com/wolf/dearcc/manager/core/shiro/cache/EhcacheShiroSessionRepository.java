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
import org.apache.shiro.session.mgt.ValidatingSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Console;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


public class EhCacheShiroSessionRepository implements ShiroSessionRepository {

    public static final String SHIRO_SESSION_PRE = "SHSS:";
    public static final String SHIRO_SESSION_PRE_ALL = "*SHSS:*";
    private static final String SHIRO_CACHE_NAME = "session";

    @Autowired
    private EhCacheManager ehCacheManager;

    public EhCacheShiroSessionRepository(EhCacheManager ehCacheManager) {
        this.ehCacheManager = ehCacheManager;
    }

    @Override
    public void saveSession(Session session) {

        if (session == null || session.getId() == null) {
            return;
            //throw new NullPointerException("session is empty");
        }
        try {
            //如果会话过期/停止 没必要再更新了
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }
            //如果主要值没变，也不要更新
            if (session instanceof SimpleSessionEx) {
                // 如果没有主要字段发生改变
                SimpleSessionEx ss = (SimpleSessionEx) session;
                if (!ss.isChanged()) {
                    return;
                }
                ss.setChanged(false);
            }
            String key = buildEhCacheSessionKey(session.getId());
            //更新
            byte[] value = ProtostuffUtil.serialize(session);
            ehCacheManager.getCache(SHIRO_CACHE_NAME).put(key, value);

        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "save session error，id:[%s]", session.getId());
        }
    }

    @Override
    public void deleteSession(Serializable sessionId) {
        if (sessionId == null) {
            return;
            //throw new NullPointerException("session id is empty");
        }
        try {
            String key = buildEhCacheSessionKey(sessionId);
            ehCacheManager.getCache(SHIRO_CACHE_NAME).remove(key);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]", sessionId);
        }
    }

    @Override
    public Session getSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
            //throw new NullPointerException("session id is empty");
        }
        Session session = null;
        try {

            String key = buildEhCacheSessionKey(sessionId);
            byte[] value = (byte[]) ehCacheManager.getCache(SHIRO_CACHE_NAME).get(key);
            session = ProtostuffUtil.deserialize(value, SimpleSessionEx.class);
        } catch (Exception e) {
            SimpleSessionEx sessionEx = new SimpleSessionEx();
            sessionEx.setId(sessionId);
            session = sessionEx;
            LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]", sessionId);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        return null;
    }

    @Override
    public String getSessonId(Integer userId) {
        String key = buildEhCacheSessionSetKey(userId);
        Object value = ehCacheManager.getCache(SHIRO_CACHE_NAME).get(key);

        return value == null ? "" : value.toString();
    }

    @Override
    public void deleteSessionId(Integer userId) {
        String key = buildEhCacheSessionSetKey(userId);
        ehCacheManager.getCache(SHIRO_CACHE_NAME).remove(key);
    }

    @Override
    public void setSessionId(Integer userId, String sessionId) {
        String key = buildEhCacheSessionSetKey(userId);
        ehCacheManager.getCache(SHIRO_CACHE_NAME).put(key, sessionId);
    }

    private String buildEhCacheSessionKey(Serializable sessionId) {
        return SHIRO_SESSION_PRE + sessionId;
    }

    private String buildEhCacheSessionSetKey(Serializable sessionId) {
        return SHIRO_SESSION_PRE_ALL + sessionId;
    }
}
