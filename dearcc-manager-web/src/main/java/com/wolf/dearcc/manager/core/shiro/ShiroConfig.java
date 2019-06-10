package com.wolf.dearcc.manager.core.shiro;

import com.wolf.dearcc.manager.core.shiro.cache.EhCacheShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.cache.RedisShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.cache.ShiroCacheManager;
import com.wolf.dearcc.manager.core.shiro.cache.impl.CustomShiroCacheManager;
import com.wolf.dearcc.manager.core.shiro.cache.impl.EhCacheShiroCacheManager;
import com.wolf.dearcc.manager.core.shiro.cache.impl.RedisShiroCacheManager;
import com.wolf.dearcc.manager.core.shiro.filter.LoginFilter;
import com.wolf.dearcc.manager.core.shiro.listenter.CustomSessionListener;
import com.wolf.dearcc.manager.core.shiro.session.CustomSessionManager;
import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.token.SampleRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 *
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

		filterChainDefinitionMap.put("/api/none/sign/in", "anon");


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
		securityManager.setSessionManager(sessionManager());
		securityManager.setCacheManager(customShiroCacheManager());
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











	@Value("${my.cacheType}")
	private String cacheType;



	@Bean
	public SessionIdGenerator sessionIdGenerator(){
		return  new org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator();
	}

	@Bean
	public ShiroSessionRepository shiroSessionRepository(){
		if(cacheType.equals("redis")){
			return new RedisShiroSessionRepository();
		}
		return new EhCacheShiroSessionRepository();
	}

	@Bean
	public ShiroCacheManager shiroCacheManager(){

		if(cacheType.equals("redis")){
			RedisShiroCacheManager redisShiroCacheManager = new RedisShiroCacheManager();
			redisShiroCacheManager.setRedisTemplate(redis1);
			return redisShiroCacheManager;
		}

		EhCacheShiroCacheManager ehCacheShiroCacheManager = new EhCacheShiroCacheManager();
		ehCacheShiroCacheManager.setEhCacheManager(ehCacheManager());
		return ehCacheShiroCacheManager;
	}


	/**
	 * shiro session的管理
	 */
	@Bean
	public DefaultSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
		sessionManager.setSessionDAO(customShiroSessionDAO());

		//设置在cookie中的sessionId名称
		sessionManager.setSessionIdCookie(simpleCookie());

		sessionManager.setSessionFactory(new SimpleSessionExFactory());

		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new CustomSessionListener());
		sessionManager.setSessionListeners(listeners);
		return sessionManager;
	}

	@Bean
	public SimpleCookie simpleCookie(){

		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setName("shiro.session.id");

		return simpleCookie;
	}

	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redis1;

	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManager(ehCacheManagerFactoryBean());
		return ehCacheManager;
	}

	/**
	 * 管理缓存 解决热部署 Ehcache重复创建CacheManager问题
	 * @return
	 */
	@Bean(name = "ehcacheManager")
	public CacheManager ehCacheManagerFactoryBean() {
		CacheManager cacheManager = CacheManager.getCacheManager("es");
		if(cacheManager == null){
			try {
				cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:ehcache.xml"));
			} catch (IOException e) {
				throw new RuntimeException("initialize cacheManager failed");
			}
		}
		return cacheManager;
	}



	/**
	 *
	 */
	@Bean
	public CustomShiroCacheManager customShiroCacheManager(){
		CustomShiroCacheManager customShiroCacheManager = new CustomShiroCacheManager();
		customShiroCacheManager.setShiroCacheManager(shiroCacheManager());
		return customShiroCacheManager;
	}


	/**
	 *
	 */
	@Bean
	public CustomShiroSessionDAO customShiroSessionDAO() {
		CustomShiroSessionDAO customShiroSessionDAO = new CustomShiroSessionDAO();
		customShiroSessionDAO.setShiroSessionRepository(shiroSessionRepository());
		customShiroSessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return customShiroSessionDAO;
	}


	/**
	 *
	 */
	@Bean
	public CustomSessionManager customSessionManager(){
		CustomSessionManager customSessionManager = new CustomSessionManager();
		customSessionManager.setShiroSessionRepository(shiroSessionRepository());
		customSessionManager.setCustomShiroSessionDAO(customShiroSessionDAO());
		return customSessionManager;
	}

}
