package com.booxware.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main appication class.
 * 
 * @author ricardopalvesjr
 *
 */
@ComponentScan("com.booxware.task")
@SpringBootApplication
public class BooxwareTaskApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(BooxwareTaskApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(BooxwareTaskApplication.class)
				.run(args);

		MessageSource messageSource = context.getBean(MessageSource.class);
		LOGGER.info(messageSource.getMessage("info.process.start", null, null));
	}
}
