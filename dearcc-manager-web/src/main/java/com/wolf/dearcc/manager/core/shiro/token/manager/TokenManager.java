package com.wolf.dearcc.manager.core.shiro.token.manager;

import java.util.List;

import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.SerializeUtil;
import com.wolf.dearcc.manager.core.shiro.bo.UUser;
import com.wolf.dearcc.manager.core.shiro.session.CustomSessionManager;
import com.wolf.dearcc.manager.core.shiro.token.SampleRealm;
import com.wolf.dearcc.manager.core.shiro.token.ShiroToken;
import com.wolf.dearcc.manager.core.utils.SpringContextUtil;
import com.wolf.dearcc.pojo.PtUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Shiro管理下的Token工具类
 *
 *
 */
public class TokenManager {
    static final Class<?> CLAZZ = TokenManager.class;
    //用户登录管理
    private static SampleRealm sampleRealm = SpringContextUtil.getBean("sampleRealm",SampleRealm.class);
    //用户session管理
    public static final CustomSessionManager customSessionManager = SpringContextUtil.getBean("customSessionManager",CustomSessionManager.class);

    private static final String UUserKey = "TMU:";
    public static final String SessionKey = "TMS:";
    /**
     * 获取当前登录的用户User对象
     * @return
     */
    public static UUser getToken(ServletRequest request){
        UUser token;
        if(request!=null) {
            token = (UUser) request.getAttribute(UUserKey);
            if (token != null) {
                LoggerUtils.info(CLAZZ, "从request上下文获取当前UUser");
                return token;
            }
        }
        token = (UUser) SecurityUtils.getSubject().getPrincipal();
        if (token != null) {
            LoggerUtils.warn(CLAZZ, "从cache中获取当前UUser" + token);
        }
        if(request!=null) {
            request.setAttribute(UUserKey, token);
        }
        return (UUser) SecurityUtils.getSubject().getPrincipal() ;
    }


    /**
     * 获取当前用户的Session
     * @return
     */
    public static Session getSession(ServletRequest request){
        Session session;
        if(request!=null) {
             session = (Session) request.getAttribute(SessionKey);
            if (session != null) {
                LoggerUtils.info(CLAZZ, "从request上下文获取当前用户的Session");
                return session;
            }
        }
        session = SecurityUtils.getSubject().getSession();
        if(session!=null) {
            LoggerUtils.warn(CLAZZ, "从cache中获取当前用户的Session" + session);
        }
        if(request!=null) {
            request.setAttribute(SessionKey, session);
        }
        return  session;
    }

    /**
     * 当前服务器存储的SessionId
     * @return
     */
    public static String getSessionId(ServletRequest request){
        UUser user = getToken(request);
        if(user!=null){
            Integer userId = getToken(request).getId();
            return userId==null?null:customSessionManager.getSessionId(userId);
        }
        return  null;
    }

    public static void deleteSessionId(ServletRequest request) {
        UUser user = getToken(request);
        if(user!=null) {
            Integer userId = user.getId();
            if (userId != null) {
                customSessionManager.deleteSessionId(userId);
            }
        }
    }

    public static void setSessionId(ServletRequest request,String sessionId) {
        UUser user = getToken(request);
        if(user!=null) {
            Integer userId = user.getId();
            if (userId != null) {
                customSessionManager.setSessionId(userId, sessionId);
            }
        }
    }

    /**
     * 登录
     */
    public static UUser login(HttpServletRequest request,String account,String password, Boolean rememberMe){
        ShiroToken token = new ShiroToken(account, password);
        token.setRememberMe(rememberMe);
        SecurityUtils.getSubject().login(token);
        return getToken(null);
    }


    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin() {
        return null != SecurityUtils.getSubject().getPrincipal();
    }
    /**
     * 退出登录
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

}
