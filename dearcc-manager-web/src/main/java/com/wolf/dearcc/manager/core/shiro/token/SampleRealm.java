package com.wolf.dearcc.manager.core.shiro.token;

import com.wolf.dearcc.common.utils.StringUtils;
import com.wolf.dearcc.manager.core.shiro.bo.SimpleSessionEx;
import com.wolf.dearcc.manager.core.shiro.bo.UUser;
import com.wolf.dearcc.manager.core.shiro.session.CustomSessionManager;
import com.wolf.dearcc.manager.core.shiro.session.SessionStatus;
import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.token.manager.TokenManager;
import com.wolf.dearcc.pojo.PtUser;
import com.wolf.dearcc.service.PtPermissionService;
import com.wolf.dearcc.service.PtRoleService;
import com.wolf.dearcc.service.PtUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;


/**
 *
 * shiro 认证 + 授权   重写
 * 
 */
public class SampleRealm extends AuthorizingRealm {

	@Autowired
	PtUserService userService;
	@Autowired
	PtPermissionService permissionService;
	@Autowired
	PtRoleService roleService;

	//0:禁止登录
	public static final short forbit = 1;
	//1:有效
	public static final short valid = 0;

	public SampleRealm() {
		super();
	}
	/**
	 *  认证信息，主要针对用户登录， 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		
		ShiroToken token = (ShiroToken) authcToken;
		PtUser user = userService.login(token.getUsername(),token.getPswd());
		if(null == user){
			throw new AccountException("帐号或密码不正确！");
		/**
		 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
		 */
		}else if(user.getDeleteFlag()==forbit){
			throw new DisabledAccountException("帐号已经禁止登录！");
		}else{
			//更新登录时间 last login time
			user.setLastLoginTime(new Date());
			userService.updateByPrimaryKeySelective(user);
		}

		UUser uuser = new UUser(user);
		SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(uuser,user.getPassword(), getName());

		//互踢
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//获取当前用户保存的SessionId，用于互踢，如果为空，说明当前服务器上未登陆该用户
		String singleSessionId = TokenManager.getSessionId(request);
		//当前用户的SessionId
        String sessionId = TokenManager.getSession(request).getId().toString();
        //如果sessionId不一样，说明同一个账号登陆多个地方
		if (StringUtils.isBlank(singleSessionId) || !sessionId.equals(singleSessionId)) {

			TokenManager.setSessionId(request,sessionId);
		}
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = roleService.findRoleByUserId(user.getId());
		uuser.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionByUserId(user.getId());
		uuser.setStringPermissions(permissions);

		return  sai;
    }

	/**
	 * 授权 其实这里我绕过去了，基本上不会用到，因为我授权是从session里面的UUSer去获取，再根据URI进行判断
	 */
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		UUser user = TokenManager.getToken(request);
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		info.setRoles(user.getRoles());
		info.setStringPermissions(user.getStringPermissions());
        return info;  
    }

	/**
	 * 授权 每次请求授权验证，这个方法都要执行一遍，~~
	 */
    @Override
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			return null;
		} else {
			return doGetAuthorizationInfo(principals);
		}
	}

    /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
