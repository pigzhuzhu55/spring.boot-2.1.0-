package com.wolf.dearcc.manager.core.shiro;

import com.wolf.dearcc.manager.core.shiro.bo.SimpleSessionEx;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

public class SimpleSessionExFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        if (initData != null) {
            String host = initData.getHost();
            if (host != null) {
                return new SimpleSessionEx(host);
            }
        }
        return new SimpleSessionEx();
    }
}
