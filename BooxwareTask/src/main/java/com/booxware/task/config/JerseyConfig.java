package com.booxware.task.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import com.booxware.task.rest.LoginResource;

/**
 * Jersey configuration class.
 * 
 * @author ricardopalvesjr
 *
 */
@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	/**
	 * Constructor method.
	 */
	public JerseyConfig() {
		this.register(LoginResource.class);
		this.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	}

}
