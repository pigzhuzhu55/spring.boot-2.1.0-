package com.wolf.dearcc.manager.core.shiro.session;

import org.apache.shiro.session.Session;
import java.io.Serializable;
import java.util.Collection;

/**
 * Session操作
 */
public interface ShiroSessionRepository {

    /**
     * 存储Session
     *
     * @param session
     */
    void saveSession(Session session,Boolean isNew);

    /**
     * 删除session
     *
     * @param sessionId
     */
    void deleteSession(Serializable sessionId);

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    Session getSession(Serializable sessionId);

    /**
     * 获取所有sessoin
     *
     * @return
     */
    Collection<Session> getAllSessions();

    String getSessonId(Integer userId);
    void deleteSessionId(Integer userId);
    void setSessionId(Integer userId,String sessionId);
}
