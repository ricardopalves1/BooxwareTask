package com.booxware.task.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;

import com.booxware.task.security.SecurityCsrfFilter;
import com.booxware.task.security.SecurityPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	SecurityCsrfFilter securityCsrfFilter;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		String[] patterns = new String[] { "/", "/booxware/login", "/booxware/register/**" };

		http.httpBasic().realmName("Booxware");

		http.csrf().csrfTokenRepository(csrfTokenRepository())
				.requireCsrfProtectionMatcher(new SecurityPathRequestMatcher(patterns)).and()
				.addFilterAfter(securityCsrfFilter, SessionManagementFilter.class);

		http.authorizeRequests().antMatchers("/booxware/register/**").permitAll();
		http.authorizeRequests().antMatchers("/booxware/unregister/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/booxware/**").hasAnyRole("USER", "ADMIN");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username, password, enabled from accounts where username=?")
				.authoritiesByUsernameQuery("select username, role from account_roles where username=?");
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("BOOXWARE-CSRF-TOKEN");

		return repository;
	}

}
