package com.wolf.dearcc.manager.core.shiro.filter;

import com.wolf.dearcc.common.model.ApiResult;
import com.wolf.dearcc.common.utils.LoggerUtils;
import com.wolf.dearcc.common.utils.StringUtils;
import com.wolf.dearcc.manager.core.shiro.bo.UUser;
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

		UUser token = TokenManager.getToken();

		if(null != token || isLoginRequest(request, response)){// && isEnabled()


			//互踢
			//获取当前用户保存的SessionId，用于互踢，如果为空，说明当前服务器上未登陆该用户
			String singleSessionId = TokenManager.getSessionId();
			//当前用户的SessionId
			String sessionId = TokenManager.getSession().getId().toString();
			//如果sessionId不一样，说明同一个账号登陆多个地方
			if (StringUtils.isNotBlank(singleSessionId) && !sessionId.equals(singleSessionId)) {
				ShiroFilterUtils.out(response, ApiResult.Fail("您的账号在别处登陆！"));
				return false;
			}


			return true;
        }


		if (ShiroFilterUtils.isAjax(request)) {// ajax请求f
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			ShiroFilterUtils.out(response, ApiResult.Fail("当前用户没有登录"));
		}

		return false ;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		if (!ShiroFilterUtils.isAjax(request)) {
			//保存Request和Response 到登录后的链接
			saveRequestAndRedirectToLogin(request, response);
		}
		return false;
	}

}
