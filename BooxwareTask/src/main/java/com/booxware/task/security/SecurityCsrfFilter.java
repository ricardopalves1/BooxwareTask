package com.booxware.task.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Csrf filter.
 * 
 * @author ricardopalvesjr
 *
 */
@Component
public class SecurityCsrfFilter extends GenericFilterBean {

	private static final String CSRF_TOKEN = "BOOXWARE-CSRF-TOKEN";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		CsrfToken csrf = (CsrfToken) servletRequest.getAttribute(CsrfToken.class.getName());
		String token = csrf.getToken();

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (null != token && !response.containsHeader(CSRF_TOKEN)) {
			Cookie cookie = new Cookie(CSRF_TOKEN, token);
			cookie.setPath("/");
			response.addCookie(cookie);
			response.addHeader(CSRF_TOKEN, token);
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

}
