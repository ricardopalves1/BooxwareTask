package com.booxware.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.booxware.task.domain.Account;
import com.booxware.task.exception.AccountServiceException;
import com.booxware.task.persistence.PersistenceInterface;

/**
 * Service implementation for account management.
 * 
 * @author ricardopalvesjr
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AccountService implements AccountServiceInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceInterface.class);

	@Autowired
	PersistenceInterface persistence;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.service.AccountServiceInterface#login()
	 */
	@Override
	public Account login() throws AccountServiceException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();

		Account account = new Account();
		account.setUsername(userDetails.getUsername());

		try {
			persistence.updateLastlogin(account);
			LOGGER.debug(messageSource.getMessage("debug.lastlogin.update.success",
					new Object[] { account.getUsername() }, null));

		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new AccountServiceException(e.getMessage());
		}

		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.service.AccountServiceInterface#register(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Account register(String username, String email, String password) throws AccountServiceException {
		Account account = new Account();
		account.setUsername(username);
		account.setEmail(email);

		password = passwordEncoder.encode(password);
		account.setPassword(password);

		try {
			persistence.save(account);
			persistence.saveRole(account);

			List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_USER");
			User user = new User(account.getUsername(), account.getPassword(), authorityList);
			Authentication auth = new UsernamePasswordAuthenticationToken(user, password, authorityList);
			SecurityContextHolder.getContext().setAuthentication(auth);
			LOGGER.debug(messageSource.getMessage("debug.login.success", new Object[] { account.getUsername() }, null));

			return account;

		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new AccountServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.service.AccountServiceInterface#deleteAccount(java.lang.
	 * String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAccount(String username) throws AccountServiceException {
		try {
			Account account = persistence.findByName(username);
			persistence.deleteRole(account);
			persistence.delete(account);
			LOGGER.debug(
					messageSource.getMessage("debug.delete.success", new Object[] { account.getUsername() }, null));

		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new AccountServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.service.AccountServiceInterface#hasLoggedInSince(java.lang.
	 * String, java.util.Date)
	 */
	@Override
	public boolean hasLoggedInSince(String username, Date date) throws AccountServiceException {
		try {
			Account account = persistence.findByName(username);
			Date lastLogin = account.getLastLogin();

			ZoneId defaultZoneId = ZoneId.systemDefault();
			LocalTime midnight = LocalTime.MIDNIGHT;

			LocalDate localDate = lastLogin.toInstant().atZone(defaultZoneId).toLocalDate();
			LocalDateTime dateMidnight = LocalDateTime.of(localDate, midnight);
			lastLogin = Date.from(dateMidnight.atZone(defaultZoneId).toInstant());

			boolean hasLogged = date.after(lastLogin);
			LOGGER.debug(messageSource.getMessage("debug.hasLoggedInSince",
					new Object[] { account.getUsername(), hasLogged }, null));

			return hasLogged;

		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new AccountServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.service.AccountServiceInterface#findAll()
	 */
	@Override
	public List<Account> findAll() throws AccountServiceException {
		List<Account> allAccounts = persistence.findAll();
		allAccounts.stream().forEach(x -> LOGGER.debug(x.toString()));

		return allAccounts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.service.AccountServiceInterface#updateLastLogin(java.lang.
	 * String)
	 */
	@Override
	public void updateLastLogin(String username) throws AccountServiceException {
		try {
			Account account = persistence.findByName(username);
			persistence.updateLastlogin(account);

		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new AccountServiceException(e.getMessage());
		}

	}

}
