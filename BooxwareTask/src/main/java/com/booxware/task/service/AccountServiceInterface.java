package com.booxware.task.service;

import java.util.Date;
import java.util.List;

import com.booxware.task.domain.Account;
import com.booxware.task.exception.AccountServiceException;

/**
 * Service for account management.
 */
public interface AccountServiceInterface {

	/**
	 * Logs in the user, if the username exists and the password is correct. Updates
	 * the last login date.
	 * 
	 * 
	 * @return the logged in account
	 * 
	 * @throws AccountServiceException
	 *             if any errors occur
	 */
	public Account login() throws AccountServiceException;

	/**
	 * Registers a new Account, if the username doesn't exist yet and logs in the
	 * user.
	 * 
	 * @param username
	 *            the User's name
	 * @param email
	 *            the email address of the user
	 * @param password
	 *            the clear text password
	 * @return the newly registered Account
	 * 
	 * @throws AccountServiceException
	 *             if any errors occur
	 */
	public Account register(String username, String email, String password) throws AccountServiceException;

	/**
	 * Deletes an Account, if the user exist.
	 * 
	 * @param username
	 *            the User's name
	 * 
	 * @throws AccountServiceException
	 *             if any errors occur
	 */
	public void deleteAccount(String username) throws AccountServiceException;

	/**
	 * Checks if a user has logged in since a provided timestamp.
	 * 
	 * @param username
	 *            the User's name
	 * @param date
	 *            Date Date received to compare to.
	 * @return true if the user has logged in since the provided timestamp, else
	 *         false.
	 * @throws AccountServiceException
	 *             if any error occurs
	 */
	public boolean hasLoggedInSince(String username, Date date) throws AccountServiceException;

	/**
	 * Finds all users registered.
	 * 
	 * @return List<Account> List of all accounts registered.
	 * @throws AccountServiceException
	 *             if any error occurs
	 */
	public List<Account> findAll() throws AccountServiceException;

	/**
	 * Updates the last login date.
	 * 
	 * @param username
	 *            the User's name
	 * @throws AccountServiceException
	 */
	public void updateLastLogin(String username) throws AccountServiceException;
}
