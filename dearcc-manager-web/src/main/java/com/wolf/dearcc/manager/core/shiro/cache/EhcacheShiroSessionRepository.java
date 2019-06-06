package com.wolf.dearcc.manager.core.shiro.cache;

import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;


public class EhcacheShiroSessionRepository implements ShiroSessionRepository {

    @Override
    public void saveSession(Session session) {

    }

    @Override
    public void deleteSession(Serializable sessionId) {

    }

    @Override
    public Session getSession(Serializable sessionId) {
        return null;
    }

    @Override
    public Collection<Session> getAllSessions() {
        return null;
    }
}
