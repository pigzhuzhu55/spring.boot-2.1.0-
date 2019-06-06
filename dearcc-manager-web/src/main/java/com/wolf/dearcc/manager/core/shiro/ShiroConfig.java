package com.wolf.dearcc.manager.core.shiro;

import com.wolf.dearcc.manager.core.shiro.cache.EhcacheShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.filter.LoginFilter;
import com.wolf.dearcc.manager.core.shiro.listenter.CustomSessionListener;
import com.wolf.dearcc.manager.core.shiro.session.CustomSessionManager;
import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.token.SampleRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 *
 */
@Configuration
public class ShiroConfig {

	private final static int tomcatTimeout = 1800;

	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		LinkedHashMap<String, Filter> filter = new LinkedHashMap<>();
		filter.put("loginFilter", new LoginFilter());
		//filter.put("perms", new ShiroPermissionsFilter());
		shiroFilterFactoryBean.setFilters(filter);
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 配置不会被拦截的链接
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/doc.html", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/v2/api-docs/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");


		filterChainDefinitionMap.put("/**", "loginFilter,authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	@Bean
	public PassThruAuthenticationFilter getFilter() {
		PassThruAuthenticationFilter filter = new PassThruAuthenticationFilter();
		return filter;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(sampleRealm());
		securityManager.setCacheManager(ehCacheManager());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	SampleRealm sampleRealm() {
		SampleRealm sampleRealm = new SampleRealm();
		return sampleRealm;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public CustomShiroSessionDAO customShiroSessionDAO() {
		CustomShiroSessionDAO customShiroSessionDAO = new CustomShiroSessionDAO();
		customShiroSessionDAO.setShiroSessionRepository(shiroSessionRepository());
		return customShiroSessionDAO;
	}

	@Bean
	public ShiroSessionRepository shiroSessionRepository(){
		return new EhcacheShiroSessionRepository();
	}


	/**
	 * shiro session的管理
	 */
	@Bean
	public DefaultSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
		sessionManager.setSessionDAO(customShiroSessionDAO());

//		SimpleCookie simpleCookie = new SimpleCookie();
//		simpleCookie.setName("dearcc.session.id");
//		simpleCookie.setMaxAge(-1);
//		simpleCookie.setHttpOnly(true);
//		sessionManager.setSessionIdCookie(simpleCookie);
//		sessionManager.setSessionIdCookieEnabled(true);

		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new CustomSessionListener());
		sessionManager.setSessionListeners(listeners);
		return sessionManager;
	}

	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache.xml");
		return em;
	}

	@Bean
	public CustomSessionManager customSessionManager(){
		CustomSessionManager customSessionManager = new CustomSessionManager();
		customSessionManager.setShiroSessionRepository(shiroSessionRepository());
		customSessionManager.setCustomShiroSessionDAO(customShiroSessionDAO());
		return customSessionManager;
	}


	/*
	默认的SessionDAO
	 */
	@Bean
	public SessionDAO sessionDAO() {
		return new MemorySessionDAO();
	}

}
