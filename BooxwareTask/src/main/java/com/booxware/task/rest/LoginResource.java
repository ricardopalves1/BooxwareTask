package com.booxware.task.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.booxware.task.domain.Account;
import com.booxware.task.exception.AccountServiceException;
import com.booxware.task.service.AccountServiceInterface;

/**
 * Rest implementation for account management.
 * 
 * @author ricardopalvesjr
 *
 */
@Component
public class LoginResource implements LoginResourceInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginResource.class);

	@Autowired
	private AccountServiceInterface accountService;

	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.rest.LoginResourceInterface#login()
	 */
	@Override
	public Response login() {
		Account account = null;
		try {
			account = accountService.login();
			LOGGER.info(messageSource.getMessage("info.login.success", new Object[] { account.getUsername() }, null));

		} catch (AccountServiceException e) {
			LOGGER.error(e.getMessage(), e);
			return Response.status(Response.Status.PRECONDITION_FAILED).entity(e.getMessage()).build();
		}

		return Response.ok().entity(account).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.rest.LoginResourceInterface#register(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Response register(String username, String password, String email) {
		Account account = null;
		try {
			account = accountService.register(username, email, password);
			accountService.login();
			LOGGER.info(
					messageSource.getMessage("info.register.success", new Object[] { account.getUsername() }, null));

			return Response.ok().entity(account).build();

		} catch (AccountServiceException e) {
			LOGGER.error(e.getMessage(), e);
			return Response.status(Response.Status.PRECONDITION_FAILED).entity(e.getMessage()).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.rest.LoginResourceInterface#unregister(java.lang.String)
	 */
	@Override
	public Response unregister(String username) {
		String unregister = String.format("[{\"username\":\"%s\"}]", username);
		try {
			accountService.deleteAccount(username);
			LOGGER.info(messageSource.getMessage("info.unregister.success", new Object[] { username }, null));

			return Response.ok().entity(unregister).build();

		} catch (AccountServiceException e) {
			LOGGER.error(e.getMessage(), e);
			return Response.status(Response.Status.PRECONDITION_FAILED).entity(e.getMessage()).build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.rest.LoginResourceInterface#hasLoggedInSince(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public Response hasLoggedInSince(String username, String sdate) {
		String hasLoggedInSince = String.format("[{\"username\":\"%s\", \"date\":\"%s\"}]", username, sdate);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
		Date date = null;
		String msg = null;
		try {
			date = formatter.parse(sdate);

			boolean hasLogged = accountService.hasLoggedInSince(username, date);
			if (!hasLogged) {
				msg = messageSource.getMessage("info.hasLoggedInSince.false", new Object[] { username, sdate }, null);
				LOGGER.info(msg);
				return Response.status(Response.Status.EXPECTATION_FAILED).entity(msg).build();
			}

		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
			return Response.status(Response.Status.PRECONDITION_FAILED).entity(e.getMessage()).build();
		}

		msg = messageSource.getMessage("info.hasLoggedInSince.true", new Object[] { username, sdate }, null);
		LOGGER.info(msg);
		return Response.ok().entity(hasLoggedInSince).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.rest.LoginResourceInterface#findAll()
	 */
	@Override
	public Response findAll() {
		List<Account> allAccounts = accountService.findAll();
		allAccounts.stream().forEach(account -> LOGGER.info(account.toString()));

		return Response.ok().entity(allAccounts).build();
	}

}
