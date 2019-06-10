package com.wolf.dearcc.manager.core.shiro.token;

import com.wolf.dearcc.common.utils.StringUtils;
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

import javax.annotation.PostConstruct;
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
		SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user,user.getPassword(), getName());

		String sessionId = SecurityUtils.getSubject().getSession().getId().toString();

		//互踢
		String singleSessionId = TokenManager.customSessionManager.getShiroSessionRepository().getSessonId(user.getId().toString());
		if (StringUtils.isNotBlank(singleSessionId) && !sessionId.equals(singleSessionId)) {
			TokenManager.customSessionManager.getShiroSessionRepository().deleteSession(user.getId().toString());
		}
		TokenManager.customSessionManager.getShiroSessionRepository().setSessionId(user.getId().toString(),sessionId);

		return  sai;
    }

	 /** 
     * 授权 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
    	Integer userId = TokenManager.getUserId();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = roleService.findRoleByUserId(userId);
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionByUserId(userId);
		info.setStringPermissions(permissions);
        return info;  
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
