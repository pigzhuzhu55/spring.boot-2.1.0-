package com.wolf.dearcc.manager.core.shiro.session;

import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.manager.core.shiro.token.manager.TokenManager;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

public class ShiroSessionManager extends DefaultWebSessionManager{
    public ShiroSessionManager(){
        super();
    }

    public static final String SessionKey = "SSMS:";

    static final Class<?> CLAZZ = ShiroSessionManager.class;

    //重写这个方法为了减少多次从redis中读取session（自定义redisSessionDao中的doReadSession方法）
    //TokenManager里面也有类似的上下文增强
    protected Session retrieveSession(SessionKey sessionKey) {
        //Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        if (request != null ) {
            Session session = (Session) request.getAttribute(SessionKey);
            if (session != null) {
                LoggerUtils.info(CLAZZ,"ShiroSessionManager从request上下文获取当前用户的Session");
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if (request != null&&session!=null) {
            LoggerUtils.warn(CLAZZ,"ShiroSessionManager从cache中获取当前用户的Session"+session);
        }
        request.setAttribute(SessionKey, session);
        return session;
    }

}
