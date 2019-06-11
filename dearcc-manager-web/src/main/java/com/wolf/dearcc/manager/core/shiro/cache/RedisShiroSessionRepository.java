package com.wolf.dearcc.manager.core.shiro.cache;

import com.github.pagehelper.util.StringUtil;
import com.wolf.dearcc.common.utils.DateUtils;
import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.ProtostuffUtil;
import com.wolf.dearcc.manager.core.shiro.bo.SimpleSessionEx;
import com.wolf.dearcc.manager.core.shiro.session.CustomSessionManager;
import com.wolf.dearcc.manager.core.shiro.session.SessionStatus;
import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.token.manager.TokenManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Session 管理
 *
 * @author sojson.com
 */
@SuppressWarnings("unchecked")
public class RedisShiroSessionRepository implements ShiroSessionRepository {
    public static final String SHIRO_SESSION_PRE = "SHSS:";
    public static final String SHIRO_SESSION_PRE_ALL = "*SHSS:*";

    private RedisTemplate redis;

    private ValueOperations<String, String> opsForValue;

    private HashOperations<String, String, Object> opsForHash;

    public RedisShiroSessionRepository(RedisTemplate redis) {
        this.redis = redis;
    }

    @PostConstruct
    public void init() {
        opsForValue = redis.opsForValue();
        opsForHash = redis.opsForHash();
    }


    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            String key = buildRedisSessionKey(session.getId());

            //不存在才添加。
            SessionStatus sessionStatus;
            Object sessionStatusObj = session.getAttribute(CustomSessionManager.SESSION_STATUS);
            if (null == sessionStatusObj) {
                //Session 踢出自存存储。
                sessionStatus = new SessionStatus();
                session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }
            else
            {
                 sessionStatus = (SessionStatus)sessionStatusObj;
            }

            //避免频繁写入session到redis造成的性能问题，10分钟最多写一次
            if (sessionStatus.getLoginNew()||(DateUtils.addtime(sessionStatus.getLastWriteTime(), 12, 10).getTime() - new java.util.Date().getTime()) < 100) {
                sessionStatus.setLastWriteTime(new java.util.Date());
                byte[] value = ProtostuffUtil.serialize(session);
                sessionStatus.setLoginNew(Boolean.FALSE);
                opsForValue.set(key, Base64.getEncoder().encodeToString(value), session.getTimeout(), TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "save session error，id:[%s]", session.getId());
        }
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            opsForValue.getOperations().delete(buildRedisSessionKey(id));

        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]", id);
        }
    }


    @Override
    public Session getSession(Serializable id) {
        if (id == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            String value = opsForValue.get(buildRedisSessionKey(id));
            session = ProtostuffUtil.deserialize(Base64.getDecoder().decode(value), SimpleSessionEx.class);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]", id);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        return null;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return SHIRO_SESSION_PRE + sessionId;
    }

    @Override
    public String getSessonId(Integer userId) {
        Object o = opsForHash.get(SHIRO_SESSION_PRE_ALL, userId.toString());
        return o == null ? "" : o.toString();
    }

    @Override
    public void deleteSessionId(Integer userId) {
        opsForHash.delete(SHIRO_SESSION_PRE_ALL, userId.toString());
    }

    @Override
    public void setSessionId(Integer userId, String sessionId) {
        opsForHash.put(SHIRO_SESSION_PRE_ALL, userId.toString(), sessionId);
    }

}
