package com.wolf.dearcc.manager.core.shiro.filter;

import com.wolf.dearcc.common.model.ApiResult;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MyAuthenticationFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }

        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        ShiroFilterUtils.out(response, ApiResult.Fail("权限不足！"));
        return true;
    }
}
