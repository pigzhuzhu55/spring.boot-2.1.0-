package com.wolf.dearcc.manager.core.shiro.filter;

import com.wolf.dearcc.common.model.ApiResult;
import com.wolf.dearcc.manager.core.shiro.token.manager.TokenManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 权限校验 Filter
 */
public class PermissionFilter extends AccessControlFilter {


	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

		String path = ((HttpServletRequest) request).getServletPath();
		Boolean isPermitted = false;

		Set<String> restUriPerms=TokenManager.getToken(request).getStringPermissions();
		if(restUriPerms!=null) {
			isPermitted = restUriPerms.contains(path);
		}
		if(!isPermitted)
		{
			ShiroFilterUtils.out(response, ApiResult.Fail("权限不足！"));
			return false;
		}

		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (!ShiroFilterUtils.isAjax(request)) {
			//保存Request和Response 到登录后的链接
			saveRequestAndRedirectToLogin(request, response);
		}
		return false;
	}
}
