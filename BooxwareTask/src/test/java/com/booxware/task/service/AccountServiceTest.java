/**
 * 
 */
package com.booxware.task.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.booxware.task.domain.Account;
import com.booxware.task.exception.AccountServiceException;

/**
 * Test case for AccountService.
 * 
 * @author ricardopalvesjr
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

	private final String USERNAME = "ricardo";
	private final String PASSWORD = "1";

	@Autowired
	private AccountServiceInterface accountService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		User user = new User(USERNAME, PASSWORD, authorityList);
		Authentication auth = new UsernamePasswordAuthenticationToken(user, PASSWORD, authorityList);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * Test method for {@link com.booxware.task.service.AccountService#login()}.
	 */
	@Test
	public final void testLogin() {
		try {
			Account account = accountService.login();
			assertTrue(account.getUsername().equals(USERNAME));

		} catch (AccountServiceException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.booxware.task.service.AccountService#register(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testRegister() {

		String username = "carol";
		String password = "123";
		String email = "carol@booxware.com.de";

		try {
			accountService.register(username, email, password);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) auth.getPrincipal();

			// is the new user "carol" logged in?
			assertTrue(userDetails.getUsername().equals(username));

		} catch (AccountServiceException e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Test method for
	 * {@link com.booxware.task.service.AccountService#deleteAccount(java.lang.String)}.
	 */
	@Test
	public final void testDeleteAccount() {
		String username = "carol";

		try {
			accountService.deleteAccount(username);
			assertTrue(true);

		} catch (AccountServiceException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.booxware.task.service.AccountService#hasLoggedInSince(java.lang.String, java.util.Date)}.
	 */
	@Test
	public final void testHasLoggedInSince() {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
			String sdate = "01-01-2018";
			Date date = formatter.parse(sdate);

			assertFalse(accountService.hasLoggedInSince(USERNAME, date));

		} catch (AccountServiceException e) {
			fail(e.getMessage());

		} catch (ParseException e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Test method for {@link com.booxware.task.service.AccountService#findAll()}.
	 */
	@Test
	public final void testFindAll() {
		try {
			List<Account> accountsList = accountService.findAll();
			assertTrue(accountsList.size() == 2);

		} catch (AccountServiceException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.booxware.task.service.AccountService#updateLastLogin(java.lang.String)}.
	 */
	@Test
	public final void testUpdateLastLogin() {
		try {
			accountService.updateLastLogin(USERNAME);
			assertTrue(true);

		} catch (AccountServiceException e) {
			fail(e.getMessage());
		}
	}

}
