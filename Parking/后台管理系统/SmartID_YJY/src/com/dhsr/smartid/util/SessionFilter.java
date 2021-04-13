package com.dhsr.smartid.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhsr.smartid.peizhiguanli.domain.Operator;

public class SessionFilter implements Filter {
	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		if (httpServletRequest.getServletPath().indexOf("login.html") > 0 || httpServletRequest.getServletPath().indexOf("logout.html") > 0) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			Operator operator = (Operator) httpServletRequest.getSession().getAttribute("operatorSession");
			if (operator != null) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.html");
			}
		}
	}

	public void init(FilterConfig paramFilterConfig) throws ServletException {
	}
}
