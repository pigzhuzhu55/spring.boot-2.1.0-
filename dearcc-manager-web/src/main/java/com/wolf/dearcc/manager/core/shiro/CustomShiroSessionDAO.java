package com.wolf.dearcc.manager.core.shiro;

import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.manager.core.shiro.bo.SimpleSessionEx;
import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import java.io.Serializable;
import java.util.Collection;

/**
 * Session 操作
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO {

    private ShiroSessionRepository shiroSessionRepository;

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        getShiroSessionRepository().saveSession(session,false);
    }

    @Override
    public void delete(Session session) {
        if (session == null) {
            LoggerUtils.error(getClass(), "Session 不能为null");
            return;
        }
        Serializable id = session.getId();
        if (id != null)
            getShiroSessionRepository().deleteSession(id);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return getShiroSessionRepository().getAllSessions();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        ((SimpleSessionEx) session).setId(sessionId);
        //this.assignSessionId(session, sessionId);
        getShiroSessionRepository().saveSession(session,true);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {

        return getShiroSessionRepository().getSession(sessionId);
    }
}
