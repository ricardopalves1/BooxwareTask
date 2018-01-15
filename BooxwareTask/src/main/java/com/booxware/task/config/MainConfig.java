package com.booxware.task.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main application configuration class.
 * 
 * @author ricardopalvesjr
 *
 */
@Configuration
public class MainConfig {

	/**
	 * Datasource bean.
	 * 
	 * @return DataSource the instance for persistence purposes.
	 */
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setName("booxware");
		builder.setType(EmbeddedDatabaseType.DERBY);
		builder.addScript("derby/create.sql");
		builder.addScript("derby/insert.sql");

		return builder.build();
	}

	/**
	 * JdbcTemplate bean.
	 * 
	 * @return JdbcTemplate the instance for handling queries.
	 */
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	/**
	 * PasswordEncoder bean.
	 * 
	 * @return PasswordEncoder the instance to provide hashing encode.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
