package com.wolf.dearcc.manager.core.shiro.filter;

import com.wolf.dearcc.common.model.ApiResult;
import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.StringUtils;
import com.wolf.dearcc.manager.core.shiro.session.ShiroSessionRepository;
import com.wolf.dearcc.manager.core.shiro.token.manager.TokenManager;
import com.wolf.dearcc.pojo.PtUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 判断登录
 *
 *
 * 
 */
public class LoginFilter extends AccessControlFilter {


	final static Class<LoginFilter> CLASS = LoginFilter.class;
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {

		PtUser token = TokenManager.getToken();

		if(null != token || isLoginRequest(request, response)){// && isEnabled()


			//互踢
			Object singleSessionId = TokenManager.getSessionId();
			String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
			if (singleSessionId != null && !sessionId.equals(singleSessionId.toString())) {
				ShiroFilterUtils.out(response, ApiResult.Fail("您的账号在别处登陆！"));
				return Boolean.FALSE;
			}


			return Boolean.TRUE;
        }


		if (ShiroFilterUtils.isAjax(request)) {// ajax请求f
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			ShiroFilterUtils.out(response, ApiResult.Fail("当前用户没有登录"));
		}

		return Boolean.FALSE ;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		if (!ShiroFilterUtils.isAjax(request)) {
			//保存Request和Response 到登录后的链接
			saveRequestAndRedirectToLogin(request, response);
		}
		return Boolean.FALSE ;
	}

}