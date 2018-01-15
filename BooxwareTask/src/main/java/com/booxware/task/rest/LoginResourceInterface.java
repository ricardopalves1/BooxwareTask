package com.booxware.task.rest;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Rest for account management.
 * 
 * @author ricardopalvesjr
 *
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/booxware")
public interface LoginResourceInterface {

	/**
	 * Logs in the user, if the username exists and the password is correct. Updates
	 * the last login date.
	 * 
	 * @return Response Response's object according to results acquired.
	 */
	@POST
	@Path("/login")
	public Response login();

	/**
	 * Registers a new Account, if the username doesn't exist yet and logs in the
	 * user.
	 * 
	 * @param username
	 *            the User's name
	 * @param password
	 *            the clear text password
	 * @param email
	 *            the email address of the user
	 * 
	 * @return Response Response's object according to results acquired.
	 */
	@POST
	@Path("/register/username/{username}/password/{password}/email/{email}")
	public Response register(@Valid @PathParam("username") String username,
			@Valid @PathParam("password") String password, @Valid @PathParam("email") String email);

	/**
	 * Unregisters an existing account.
	 * 
	 * @param username
	 *            the User's name
	 * @return Response Response's object according to results acquired.
	 */
	@POST
	@Path("/unregister/username/{username}")
	public Response unregister(@Valid @PathParam("username") String username);

	/**
	 * Checks if a user has logged in since a provided timestamp.
	 * 
	 * @param username
	 *            the User's name
	 * @param sdate
	 *            String Date value to compare.
	 * @return Response Response's object according to results acquired.
	 */
	@POST
	@Path("/hasLoggedInSince/username/{username}/date/{date}")
	public Response hasLoggedInSince(@Valid @PathParam("username") String username,
			@Valid @PathParam("date") String sdate);

	/**
	 * Finds all users registered.
	 * 
	 * @return Response Response's object according to results acquired.
	 */
	@POST
	@Path("/findAll")
	public Response findAll();
}
