package com.booxware.task.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.booxware.task.domain.Account;
import com.booxware.task.domain.AccountRole;

/**
 * Persistence implementation.
 * 
 * @author ricardopalvesjr
 *
 */
@Repository
public class Persistence implements PersistenceInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceInterface.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.persistence.PersistenceInterface#save(com.booxware.task.
	 * domain.Account)
	 */
	@Override
	public void save(Account account) throws DataAccessException {
		String query = "INSERT INTO accounts (username, password, email, enabled) VALUES (?, ?, ?, true)";
		Object[] args = { account.getUsername(), account.getPassword(), account.getEmail() };

		try {
			jdbcTemplate.update(query, args);

		} catch (DataAccessException e) {
			LOGGER.error(messageSource.getMessage("error.save.account", new Object[] { account.getUsername() }, null));
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.persistence.PersistenceInterface#saveRole(com.booxware.task
	 * .domain.Account)
	 */
	@Override
	public void saveRole(Account account) throws DataAccessException {
		String query = "INSERT INTO account_roles (username, role) VALUES (?, 'ROLE_USER')";
		Object[] args = { account.getUsername() };

		try {
			jdbcTemplate.update(query, args);

		} catch (DataAccessException e) {
			LOGGER.error(messageSource.getMessage("error.save.role", new Object[] { account.getUsername() }, null));
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.persistence.PersistenceInterface#findById(long)
	 */
	@Override
	public Account findById(long id) throws DataAccessException {
		Account account = null;
		String query = "SELECT a.*, r.* FROM accounts a, account_roles r WHERE a.id = ? AND a.username = r.username";
		Object[] args = { id };

		try {
			account = jdbcTemplate.queryForObject(query, args, new AccountRowMapper());

		} catch (DataAccessException e) {
			LOGGER.error(messageSource.getMessage("error.findById", new Object[] { id }, null));
			throw e;
		}

		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.persistence.PersistenceInterface#findByName(java.lang.
	 * String)
	 */
	@Override
	public Account findByName(String username) throws DataAccessException {
		Account account = null;
		String query = "SELECT a.*, r.* FROM accounts a, account_roles r WHERE a.username = ? AND a.username = r.username";
		Object[] args = { username };

		try {
			account = jdbcTemplate.queryForObject(query, args, new AccountRowMapper());

		} catch (DataAccessException e) {
			LOGGER.error(messageSource.getMessage("error.findByName", new Object[] { username }, null));
			throw e;
		}

		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.persistence.PersistenceInterface#delete(com.booxware.task.
	 * domain.Account)
	 */
	@Override
	public void delete(Account account) throws DataAccessException {
		String query = "DELETE FROM accounts a WHERE a.id = ?";
		Object[] args = { account.getId() };

		try {
			jdbcTemplate.update(query, args);

		} catch (DataAccessException e) {
			LOGGER.error(messageSource.getMessage("error.delete.account", new Object[] { account.getId() }, null));
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.booxware.task.persistence.PersistenceInterface#deleteRole(com.booxware.
	 * task.domain.Account)
	 */
	@Override
	public void deleteRole(Account account) throws DataAccessException {
		String query = "DELETE FROM account_roles r WHERE r.username = ?";
		Object[] args = { account.getUsername() };

		try {
			jdbcTemplate.update(query, args);

		} catch (DataAccessException e) {
			LOGGER.error(messageSource.getMessage("error.delete.role", new Object[] { account.getUsername() }, null));
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.persistence.PersistenceInterface#updateLastlogin(com.
	 * booxware.task.domain.Account)
	 */
	@Override
	public void updateLastlogin(Account account) throws DataAccessException {
		String query = "UPDATE accounts a SET lastlogin = CURRENT_TIMESTAMP WHERE a.username = ?";
		Object[] args = { account.getUsername() };

		try {
			jdbcTemplate.update(query, args);

		} catch (DataAccessException e) {
			LOGGER.error(
					messageSource.getMessage("error.update.lastlogin", new Object[] { account.getUsername() }, null));
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.booxware.task.persistence.PersistenceInterface#findAll()
	 */
	@Override
	public List<Account> findAll() {
		String query = "SELECT a.*, r.* FROM accounts a, account_roles r WHERE a.username = r.username";
		return jdbcTemplate.query(query, new AccountRowMapper());
	}

	/**
	 * Inner Account row mapper class.
	 * 
	 * @author ricardopalvesjr
	 *
	 */
	private static class AccountRowMapper implements RowMapper<Account> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		@Override
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account account = new Account();
			account.setId(rs.getInt("id"));
			account.setUsername(rs.getString("username"));
			account.setPassword(rs.getString("password"));
			account.setEmail(rs.getString("email"));
			account.setLastLogin(rs.getTimestamp("lastlogin"));

			AccountRole role = new AccountRole();
			role.setId(rs.getInt("role_id"));
			role.setRole(rs.getString("role"));
			account.setRole(role);

			return account;
		}
	}

}
