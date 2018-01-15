package com.booxware.task.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Path request matcher for Csrf purposes.
 * 
 * @author ricardopalvesjr
 *
 */
public class SecurityPathRequestMatcher implements RequestMatcher {

	private final AndRequestMatcher andRequestMatcher;

	/**
	 * Constructor method.
	 * 
	 * @param patterns
	 *            String[] Array of strings to verify according to current request.
	 */
	public SecurityPathRequestMatcher(String[] patterns) {
		List<RequestMatcher> requestMatchers = Arrays.asList(patterns).stream()
				.map(p -> new NegatedRequestMatcher(new AntPathRequestMatcher(p))).collect(Collectors.toList());

		andRequestMatcher = new AndRequestMatcher(requestMatchers);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.util.matcher.RequestMatcher#matches(javax.
	 * servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean matches(HttpServletRequest request) {
		return andRequestMatcher.matches(request);
	}

}
