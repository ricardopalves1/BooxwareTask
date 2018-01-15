package com.booxware.task.persistence;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.booxware.task.domain.Account;

/**
 * Persistence can be very simple, for example an in memory hash map.
 * 
 */
public interface PersistenceInterface {

	/**
	 * Saves the account.
	 * 
	 * @param account
	 *            Account object received.
	 * @throws DataAccessException
	 */
	public void save(Account account) throws DataAccessException;

	/**
	 * Saves the account's role.
	 * 
	 * @param account
	 *            Account object received.
	 * @throws DataAccessException
	 */
	public void saveRole(Account account) throws DataAccessException;

	/**
	 * Find an account by its id number.
	 * 
	 * @param id
	 *            long Account's id number.
	 * @return Account object received.
	 * @throws DataAccessException
	 *             if any persistence errors occur
	 */
	public Account findById(long id) throws DataAccessException;

	/**
	 * Find an account by its username.
	 * 
	 * @param name
	 *            String the username.
	 * @return Account object received.
	 * @throws DataAccessException
	 *             if any persistence errors occur
	 */
	public Account findByName(String name) throws DataAccessException;

	/**
	 * Deletes an account.
	 * 
	 * @param account
	 *            Account object received.
	 * @throws DataAccessException
	 *             if any persistence errors occur
	 */
	public void delete(Account account) throws DataAccessException;

	/**
	 * Deletes an account's role.
	 * 
	 * @param account
	 *            Account object received.
	 * @throws DataAccessException
	 *             if any persistence errors occur
	 */
	public void deleteRole(Account account) throws DataAccessException;

	/**
	 * Updates the account's last login.
	 * 
	 * @param account
	 *            Account object received.
	 * @throws DataAccessException
	 *             if any persistence errors occur
	 */
	public void updateLastlogin(Account account) throws DataAccessException;

	/**
	 * Finds all accounts.
	 * 
	 * @return List<Account> List of accounts registered.
	 */
	public List<Account> findAll();

}
