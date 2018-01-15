package com.booxware.task.domain;

import java.io.Serializable;

/**
 * Account's Role class.
 * 
 * @author ricardopalvesjr
 *
 */
public class AccountRole implements Serializable {

	private static final long serialVersionUID = 7238370789607420117L;

	private int id;
	private String role;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "com.booxware.task.domain.AccountRole[ id=" + id + ":role=" + role + "]";
	}
}
